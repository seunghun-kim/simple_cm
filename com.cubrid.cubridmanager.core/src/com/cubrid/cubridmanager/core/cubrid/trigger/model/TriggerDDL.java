package com.cubrid.cubridmanager.core.cubrid.trigger.model;

import java.math.BigDecimal;

import com.cubrid.common.core.common.model.Trigger;
import com.cubrid.common.core.util.QuerySyntax;
import com.cubrid.common.core.util.StringUtil;

public final class TriggerDDL {
	//Constructor
	private TriggerDDL() {
		//empty
	}

	static String newLine = StringUtil.NEWLINE;
	static String endLineChar = ";";

	/**
	 * Get the ddl
	 * 
	 * @param trigger Trigger
	 * @return String
	 */
	public static String getDDL(Trigger trigger) {
		StringBuffer bf = new StringBuffer();
		appendHead(trigger, bf);
		appendStatus(trigger, bf);
		appendPriority(trigger, bf);
		appendEvent(trigger, bf);
		appendCondition(trigger, bf);
		appendAction(trigger, bf);
		appendComment(trigger, bf);
		return bf.toString();
	}

	/**
	 * COMMENT 'user defined comment' [ ; ]
	 *
	 * @param trigger Trigger
	 * @param bf StringBuffer
	 */
	public static void appendComment(Trigger trigger, StringBuffer bf) {
		String description = trigger.getDescription();
		if (StringUtil.isNotEmpty(description)) {
			description = String.format("'%s'", description);
			bf.append(String.format(" COMMENT %s", StringUtil.escapeQuotes(description)));
		}
		bf.append(endLineChar);
	}

	/**
	 * EXECUTE [ AFTER | DEFERRED ] action [ ; ]
	 * 
	 * @param trigger Trigger
	 * @param bf StringBuffer
	 */
	private static void appendAction(Trigger trigger, StringBuffer bf) {
		String actionTime = trigger.getActionTime();
		//EXECUTE [ AFTER | DEFERRED ] action [ ; ]
		bf.append("EXECUTE ");
		if (!actionTime.equalsIgnoreCase("default")) {
			bf.append(actionTime);
			bf.append(" ");
		}
		String actionType = trigger.getActionType();
		String action = trigger.getAction();
		if ("REJECT".equals(actionType)
				|| "INVALIDATE TRANSACTION".equals(actionType)) {
			bf.append(actionType);
		} else if ("PRINT".equals(actionType)) {
			bf.append(actionType);
			bf.append(newLine);
			bf.append("'").append(action.replace("'", "''")).append("'");
		} else {
			bf.append(newLine);
			bf.append(action);
		}
	}

	/**
	 * Add IF Condition
	 * 
	 * @param trigger Trigger
	 * @param bf StringBuffer
	 */
	private static void appendCondition(Trigger trigger, StringBuffer bf) {
		String condition = trigger.getCondition();
		if (StringUtil.isNotEmpty(condition)) {
			bf.append("IF ").append(condition);
			bf.append(newLine);
		}
	}

	/**
	 * event_time event_type [ event_target ]
	 * 
	 * @param trigger Trigger
	 * @param bf StringBuffer
	 */
	private static void appendEvent(Trigger trigger, StringBuffer bf) {
		//event_time event_type [ event_target ]
		String conditionTime = trigger.getConditionTime();
		String eventType = trigger.getEventType();
		String targetTable = trigger.getTarget_class();
		String targetColumn = trigger.getTarget_att();
		if (conditionTime != null && conditionTime.trim().length() > 0) {
			bf.append(conditionTime).append(" ");
		}
		if (eventType != null && eventType.trim().length() > 0) {
			bf.append(eventType).append(" ");
		}

		if (targetTable == null || targetTable.trim().equals("")) {
			if (!("COMMIT".equals(eventType) || "ROLLBACK".equals(eventType))) {
				bf.append("<event_target>");
			}
			bf.append(newLine);
		} else {
			bf.append("ON ").append(QuerySyntax.escapeKeyword(targetTable));
			if (StringUtil.isNotEmpty(targetColumn)) {
				bf.append("(").append(QuerySyntax.escapeKeyword(targetColumn)).append(")");
			}
			bf.append(newLine);
		}
	}

	/**
	 * [ PRIORITY key ]
	 * 
	 * @param trigger Trigger
	 * @param bf StringBuffer
	 */
	private static void appendPriority(Trigger trigger, StringBuffer bf) {
		//[ PRIORITY key ]
		String priority = trigger.getPriority();
		try {
			BigDecimal p = new BigDecimal(priority);
			if (!p.equals(new BigDecimal("0.00"))) {
				bf.append("PRIORITY ").append(priority);
				bf.append(newLine);
			}
		} catch (NumberFormatException e) {
			bf.append("PRIORITY ").append(priority);
			bf.append(newLine);
		}
	}

	/**
	 * [ STATUS { ACTIVE | INACTIVE } ]
	 * 
	 * @param trigger Trigger
	 * @param bf StringBuffer
	 */
	private static void appendStatus(Trigger trigger, StringBuffer bf) {
		//[ STATUS { ACTIVE | INACTIVE } ]
		String status = trigger.getStatus();
		if (!"ACTIVE".equals(status)) {
			bf.append("STATUS INACTIVE");
			bf.append(newLine);
		}
	}

	/**
	 * Generate head of trigger clause.
	 * 
	 * @param trigger Trigger
	 * @param bf StringBuffer
	 */
	private static void appendHead(Trigger trigger, StringBuffer bf) {
		//CREATE TRIGGER trigger_name
		bf.append("CREATE TRIGGER ");
		String triggerName = trigger.getName();
		if (triggerName == null || triggerName.trim().equals("")) {
			bf.append("<trigger_name>");
		} else {
			bf.append(QuerySyntax.escapeKeyword(triggerName));
		}
		bf.append(newLine);
	}

	/**
	 * Get after ddl
	 * 
	 * @param oldTrigger Trigger
	 * @param newTrigger Trigger
	 * @return String
	 */
	public static String getAlterDDL(Trigger oldTrigger, Trigger newTrigger) {
		String triggerName = oldTrigger.getName();
		String oldPriority = oldTrigger.getPriority();
		String oldStatus = oldTrigger.getStatus();
		String oldDescription = oldTrigger.getDescription();
		String newPriority = newTrigger.getPriority();
		String newStatus = newTrigger.getStatus();
		String newDescription = newTrigger.getDescription();
		StringBuffer bf = new StringBuffer();
		boolean statusChanged = false;
		if (!oldStatus.equals(newStatus)) {
			statusChanged = true;
		}
		if (statusChanged) {
			bf.append("ALTER TRIGGER ");
			bf.append(QuerySyntax.escapeKeyword(triggerName));
			bf.append(" STATUS ").append(newStatus);
			bf.append(endLineChar);
			bf.append(newLine);
		}

		boolean priorityChanged = false;
		if (!oldPriority.equals(newPriority)) {
			priorityChanged = true;
		}
		if (priorityChanged) {
			bf.append("ALTER TRIGGER ");
			bf.append(QuerySyntax.escapeKeyword(triggerName));
			bf.append(" PRIORITY ").append(newPriority);
			bf.append(endLineChar);
			bf.append(newLine);
		}

		boolean commentChanged = false;
		if (newDescription != null && !newDescription.equals(oldDescription)) {
			commentChanged = true;
		}

		if (commentChanged) {
			bf.append("ALTER TRIGGER ");
			bf.append(QuerySyntax.escapeKeyword(triggerName));
			newDescription = String.format("'%s'", newDescription);
			bf.append(String.format(" COMMENT %s", StringUtil.escapeQuotes(newDescription)));
			bf.append(endLineChar);
			bf.append(newLine);
		}

		if (statusChanged || priorityChanged || commentChanged) {
			return bf.toString();
		} else {
			return "";
		}
	}
}
