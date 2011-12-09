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
 * frentix GmbH, http://www.frentix.com
 * <p>
 */

package org.olat.course.condition.interpreter;

import org.olat.core.id.Identity;
import org.olat.course.ICourse;
import org.olat.course.editor.CourseEditorEnv;
import org.olat.course.nodes.CourseNode;
import org.olat.course.properties.CoursePropertyManager;
import org.olat.course.run.userview.UserCourseEnvironment;
import org.olat.properties.Property;

/**
 * 
 * Description:<br>
 * Function to get the users initial launch date for this course. If no
 * course has taken place so far, the date will have a future date value.
 * <P>
 * Initial Date:  12 jan. 2010 <br>
 * 
 * @author srosse, stephane.rosse@frentix.com
 */
public class GetInitialCourseLaunchDateFunction extends AbstractFunction {

	public static final String name = "getInitialCourseLaunchDate";

	/**
	 * Default constructor to use the get initial enrollment date
	 * 
	 * @param userCourseEnv
	 */
	public GetInitialCourseLaunchDateFunction(UserCourseEnvironment userCourseEnv) {
		super(userCourseEnv);
	}

	/**
	 * @see com.neemsoft.jmep.FunctionCB#call(java.lang.Object[])
	 */
	public Object call(Object[] inStack) {
		CourseEditorEnv cev = getUserCourseEnv().getCourseEditorEnv();
		if(cev != null) {
			return defaultValue();
		}

		//the real function evaluation which is used during run time
		CourseNode node = getUserCourseEnv().getCourseEnvironment().getRunStructure().getRootNode();
		CoursePropertyManager pm = getUserCourseEnv().getCourseEnvironment().getCoursePropertyManager();
		Identity identity = getUserCourseEnv().getIdentityEnvironment().getIdentity();

		Property firstTime = pm.findCourseNodeProperty(node, identity, null, ICourse.PROPERTY_INITIAL_LAUNCH_DATE);
		if (firstTime != null) {
			String firstTimeMillis = firstTime.getStringValue();
			return Double.valueOf(firstTimeMillis);
		} else {
			// what to do in case of no date available??? -> return date in the future
			return new Double(Double.POSITIVE_INFINITY);
		}
	}

	protected Object defaultValue() {
		return new Double(Double.MIN_VALUE);
	}
}