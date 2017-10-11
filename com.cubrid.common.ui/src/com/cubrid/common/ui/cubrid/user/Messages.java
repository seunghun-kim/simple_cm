package com.cubrid.common.ui.cubrid.user;

import org.eclipse.osgi.util.NLS;

import com.cubrid.common.ui.CommonUIPlugin;

public class Messages extends
NLS {

	static {
		NLS.initializeMessages(CommonUIPlugin.PLUGIN_ID
				+ ".cubrid.user.Messages", Messages.class);
	}

	public static String errInput;

	public static String addClassButtonName;
	public static String deleteClassButtonName;

	public static String tabItemGeneral;
	public static String tabItemAuthoration;
	public static String lblOldPassword;
	public static String lblNewPassword;
	public static String lblNewPasswordConf;
	public static String lblPassword;
	public static String lblPasswordConf;
	public static String lblAllUser;
	public static String grpUserGroupList;
	public static String grpUserMemberList;
	public static String errRomoveUserGroup;
	public static String tblColMemberName;
	public static String tblColClassName;
	public static String tblColClassSchematype;
	public static String tblColClassOwner;
	public static String tblColClassSuperclass;
	public static String tblColClassType;
	public static String errRemoveSysClass;
	public static String msgUserSchema;
	public static String msgVirtualClass;
	public static String msgClass;
	public static String tblColAuthTable;
	public static String tblColAuthSelect;
	public static String tblColAuthInsert;
	public static String tblColAuthUpdate;
	public static String tblColAuthDelete;
	public static String tblColAuthAlter;
	public static String tblColAuthIndex;
	public static String tblColAuthExecute;
	public static String tblColAuthGrantselect;
	public static String tblColAuthGrantinsert;
	public static String tblColAuthGrantupdate;
	public static String tblColAuthGrantdelete;
	public static String tblColAuthGrantalter;
	public static String tblColAuthGrantindex;
	public static String tblColAuthGrantexecute;
	public static String msgEditUserDialog;
	public static String msgAddUserDialog;
	public static String errPasswordDiff;
	public static String errInputPassLength;
	public static String errInputPasswordMinLength;
	public static String errInputName;
	public static String errInputNameLength;
	public static String errInputNameExist;
	public static String errInputNameContain;
	public static String errInputNameAccept;
	public static String errInputNameValidate;
	public static String msgTitleWarning;
	public static String msgQuestionSureDelete;
	public static String grpUserMemberInfo;
	public static String lblUserName;
	public static String lblUserDescription;
	public static String msgSelectDB;
	public static String errOldPassword;
	public static String grpPasswordSetting;
	public static String btnPasswordChange;
	public static String btnAddGroup;
	public static String btnRemoveGroup;
	public static String msgLogoutInfomation;
	public static String titleLogout;
	public static String errInputPassword;
	public static String errInvalidPassword;
	public static String lblAuthorizedTable;
	public static String lblUnAuthorizedTable;
	public static String lblOwnerClassList;
	public static String tblColOwnerClassName;
	public static String tblColOwnerClassSchema;
	public static String tblColOwnerClassType;
	public static String lblAuthorizationList;
	public static String lblDbaAllAuth;
	public static String msgSystemSchema;
	public static String lblGroupList;
	public static String lblGroupNotExist;
	public static String lblMemberList;
	public static String lblMemberNotExist;
	public static String viewUserInfoJobName;
	public static String addOrUpdateUserTaskName;
	public static String msgDeleteUserSuccess;

	public static String btnRefresh;
	public static String btnAddUser;
	public static String btnEditUser;
	public static String btnDropUser;
	public static String itemAddUser;
	public static String itemEditUser;
	public static String itemDropUser;
	public static String errNoUserSelected;
	public static String msgConfirmRefresh;
	public static String lblUser;
	public static String jobLoadUserData;
	public static String tabUserList;
	public static String lblOWner;
	public static String msgDoYouWantToDeleteUser;
	public static String msgAddUserSuccess;
	public static String msgModifyUserSuccess;
	public static String msgInformation;
}