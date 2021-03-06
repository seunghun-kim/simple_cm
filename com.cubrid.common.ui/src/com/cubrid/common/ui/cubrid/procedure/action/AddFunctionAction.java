package com.cubrid.common.ui.cubrid.procedure.action;

import java.util.Locale;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Shell;

import com.cubrid.common.ui.cubrid.procedure.dialog.EditFunctionDialog;
import com.cubrid.common.ui.spi.action.SelectionAction;
import com.cubrid.common.ui.spi.model.CubridDatabase;
import com.cubrid.common.ui.spi.model.ICubridNode;
import com.cubrid.common.ui.spi.model.ICubridNodeLoader;
import com.cubrid.common.ui.spi.model.ISchemaNode;
import com.cubrid.common.ui.spi.model.loader.sp.CubridFunctionFolderLoader;
import com.cubrid.common.ui.spi.model.loader.sp.CubridSPFolderLoader;
import com.cubrid.common.ui.spi.util.ActionSupportUtil;
import com.cubrid.common.ui.spi.util.CommonUITool;
import com.cubrid.cubridmanager.core.cubrid.sp.model.SPInfo;
import com.cubrid.cubridmanager.core.cubrid.user.model.DbUserInfo;

public class AddFunctionAction extends
SelectionAction {

	public static final String ID = AddFunctionAction.class.getName();

	/**
	 * The constructor
	 *
	 * @param shell
	 * @param text
	 * @param enabledIcon
	 * @param disabledIcon
	 */
	public AddFunctionAction(Shell shell, String text,
			ImageDescriptor enabledIcon, ImageDescriptor disabledIcon) {
		this(shell, null, text, enabledIcon, disabledIcon);
	}

	/**
	 * The constructor
	 *
	 * @param shell
	 * @param provider
	 * @param text
	 * @param enabledIcon
	 * @param disabledIcon
	 */
	public AddFunctionAction(Shell shell, ISelectionProvider provider,
			String text, ImageDescriptor enabledIcon,
			ImageDescriptor disabledIcon) {
		super(shell, provider, text, enabledIcon);
		this.setId(ID);
		this.setToolTipText(text);
		this.setDisabledImageDescriptor(disabledIcon);
	}

	/**
	 *
	 * @see com.cubrid.common.ui.spi.action.ISelectionAction#allowMultiSelections
	 *      ()
	 * @return false
	 */
	public boolean allowMultiSelections() {
		return true;
	}

	/**
	 * Sets this action support this object
	 *
	 * @see org.eclipse.jface.action.IAction.ISelectionAction
	 * @param obj Object
	 * @return boolean
	 */
	public boolean isSupported(Object obj) {
		return ActionSupportUtil.isSupportMultiSelection(obj, null, false);
	}

	/**
	 * Open addFunctionDialog
	 */
	public void run() { // FIXME logic code move to core module
		Shell shell = getShell();
		Object[] obj = this.getSelectedObj();
		if (!isSupported(obj[0])) {
			setEnabled(false);
			return;
		}
		ISchemaNode node = (ISchemaNode) obj[0];
		CubridDatabase database = node.getDatabase();

		EditFunctionDialog dlg = new EditFunctionDialog(shell);
		dlg.setDatabase(database);
		dlg.setNewFlag(true);
		ISelectionProvider provider = getSelectionProvider();
		if (dlg.open() == IDialogConstants.OK_ID
				&& (provider instanceof TreeViewer)) {
			ICubridNode folderNode = database.getChild(database.getId()
					+ ICubridNodeLoader.NODE_SEPARATOR
					+ CubridSPFolderLoader.SP_FOLDER_ID);
			folderNode = folderNode.getChild(folderNode.getId()
					+ ICubridNodeLoader.NODE_SEPARATOR
					+ CubridSPFolderLoader.FUNCTION_FOLDER_ID);
			TreeViewer treeViewer = (TreeViewer) provider;
			if (folderNode == null || !folderNode.getLoader().isLoaded()) {
				return;
			}
			String functionName = dlg.getFunctionName().toLowerCase(
					Locale.getDefault());
			String id = folderNode.getId() + ICubridNodeLoader.NODE_SEPARATOR
					+ functionName;
			DbUserInfo userInfo = database.getDatabaseInfo().getAuthLoginedDbUserInfo();
			SPInfo spInfo = new SPInfo(functionName);
			spInfo.setOwner(userInfo.getName());
			ICubridNode newNode = CubridFunctionFolderLoader.createFunctionNode(
					id, spInfo);
			CommonUITool.addNodeToTree(treeViewer, folderNode, newNode);

		}
	}
}
