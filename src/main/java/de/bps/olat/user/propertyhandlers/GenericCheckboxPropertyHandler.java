/**
 * <a href="http://www.openolat.org">
 * OpenOLAT - Online Learning and Training</a><br>
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); <br>
 * you may not use this file except in compliance with the License.<br>
 * You may obtain a copy of the License at the
 * <a href="http://www.apache.org/licenses/LICENSE-2.0">Apache homepage</a>
 * <p>
 * Unless required by applicable law or agreed to in writing,<br>
 * software distributed under the License is distributed on an "AS IS" BASIS, <br>
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. <br>
 * See the License for the specific language governing permissions and <br>
 * limitations under the License.
 * <p>
 * Initial code contributed and copyrighted by<br>
 * BPS Bildungsportal Sachsen GmbH, http://www.bps-system.de
 * <p>
 */
package de.bps.olat.user.propertyhandlers;

import java.util.Locale;
import java.util.Map;

import org.olat.core.gui.components.form.ValidationError;
import org.olat.core.gui.components.form.flexible.FormItem;
import org.olat.core.gui.components.form.flexible.FormItemContainer;
import org.olat.core.gui.components.form.flexible.FormUIFactory;
import org.olat.core.gui.components.form.flexible.elements.SelectionElement;
import org.olat.core.gui.formelements.CheckBoxElement;
import org.olat.core.gui.formelements.FormElement;
import org.olat.core.id.User;
import org.olat.user.AbstractUserPropertyHandler;
import org.olat.user.UserManager;

/**
 * 
 * Description: Checkbox property handler.<br>
 * 
 * <P>
 * Initial Date:  06.08.2008 <br>
 * @author bja
 */
public class GenericCheckboxPropertyHandler extends AbstractUserPropertyHandler {

/**
 * 
 * @see org.olat.user.propertyhandlers.UserPropertyHandler#addFormItem(java.util.Locale, org.olat.core.id.User, java.lang.String, boolean, org.olat.core.gui.components.form.flexible.FormItemContainer)
 */
	public FormItem addFormItem(Locale locale, User user, String usageIdentifyer, boolean isAdministrativeUser,
			FormItemContainer formItemContainer) {
		SelectionElement sElem = null;
		sElem = FormUIFactory.getInstance().addCheckboxesVertical(getName(), i18nFormElementLabelKey(), formItemContainer, new String[] { getName() }, new String[]{ "" }, null, 1);
		
		UserManager um = UserManager.getInstance();
		if ( um.isUserViewReadOnly(usageIdentifyer, this) && ! isAdministrativeUser) {
			sElem.setEnabled(false);
		}
		if (um.isMandatoryUserProperty(usageIdentifyer, this)) {
			sElem.setMandatory(true);
		}
		return sElem;
	}

/**
 * 
 * @see org.olat.user.propertyhandlers.UserPropertyHandler#getFormElement(java.util.Locale, org.olat.core.id.User, java.lang.String, boolean)
 */
	public FormElement getFormElement(Locale locale, User user, String usageIdentifyer, boolean isAdministrativeUser) {
		CheckBoxElement ui = null;
		UserManager um = UserManager.getInstance();
		
		String value = getInternalValue(user);
		boolean isEnabled = value != null && value.equals("true") ? Boolean.TRUE : Boolean.FALSE;
		ui = new CheckBoxElement(i18nFormElementLabelKey(), isEnabled);
		if ( um.isUserViewReadOnly(usageIdentifyer, this) && ! isAdministrativeUser) {
			ui.setReadOnly(true);
		}
		if (um.isMandatoryUserProperty(usageIdentifyer, this)) {
			ui.setMandatory(true);
		}
		return ui;
	}

/**
 * 
 * @see org.olat.user.propertyhandlers.UserPropertyHandler#getStringValue(org.olat.core.gui.formelements.FormElement)
 */
	public String getStringValue(FormElement ui) {
		String value = "";
		if (((CheckBoxElement) ui).isChecked())
			value = "true";
		else
			value = "false";
		
		return value;
	}

/**
 * 
 * @see org.olat.user.propertyhandlers.UserPropertyHandler#getStringValue(org.olat.core.gui.components.form.flexible.FormItem)
 */
	public String getStringValue(FormItem formItem) {
		String value = "";
		if(((org.olat.core.gui.components.form.flexible.elements.SelectionElement) formItem).isSelected(0))
			value = "true";
		else
			value = "false";
		
		return value;
	}

/**
 * 
 * @see org.olat.user.propertyhandlers.UserPropertyHandler#getStringValue(java.lang.String, java.util.Locale)
 */
	public String getStringValue(String displayValue, Locale locale) {
		return displayValue;
	}

/**
 * 
 * @see org.olat.user.propertyhandlers.UserPropertyHandler#isValid(org.olat.core.gui.formelements.FormElement, java.util.Map)
 */
	public boolean isValid(FormElement ui, Map formContext) {
		return true;
	}
	
/**
 * 
 * @see org.olat.user.propertyhandlers.UserPropertyHandler#isValid(org.olat.core.gui.components.form.flexible.FormItem, java.util.Map)
 */
	public boolean isValid(FormItem formItem, Map formContext) {
		return true;
	}

/**
 * 
 * @see org.olat.user.propertyhandlers.UserPropertyHandler#isValidValue(java.lang.String, org.olat.core.gui.components.form.ValidationError, java.util.Locale)
 */
	public boolean isValidValue(String value, ValidationError validationError, Locale locale) {
		return true;
	}

/**
 * 
 * @see org.olat.user.propertyhandlers.UserPropertyHandler#updateFormElementFromUser(org.olat.core.gui.formelements.FormElement, org.olat.core.id.User)
 */
	public void updateFormElementFromUser(FormElement ui, User user) {
		((CheckBoxElement) ui).setName(getInternalValue(user));
	}

/**
 * 
 * @see org.olat.user.propertyhandlers.UserPropertyHandler#updateUserFromFormElement(org.olat.core.id.User, org.olat.core.gui.formelements.FormElement)
 */
	public void updateUserFromFormElement(User user, FormElement ui) {
		String internalValue = getStringValue(ui);
		setInternalValue(user, internalValue);
	}

/**
 * 
 * @see org.olat.user.propertyhandlers.UserPropertyHandler#updateUserFromFormItem(org.olat.core.id.User, org.olat.core.gui.components.form.flexible.FormItem)
 */
	public void updateUserFromFormItem(User user, FormItem formItem) {
		String internalValue = getStringValue(formItem);
		setInternalValue(user, internalValue);
	}

}
