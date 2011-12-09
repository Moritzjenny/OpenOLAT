/**
* OLAT - Online Learning and Training<br>
* http://www.olat.org
* <p>
* Licensed under the Apache License, Version 2.0 (the "License"); <br>
* you may not use this file except in compliance with the License.<br>
* You may obtain a copy of the License at
* <p>
* http://www.apache.org/licenses/LICENSE-2.0
* <p>
* Unless required by applicable law or agreed to in writing,<br>
* software distributed under the License is distributed on an "AS IS" BASIS, <br>
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. <br>
* See the License for the specific language governing permissions and <br>
* limitations under the License.
* <p>
* Copyright (c) since 2004 at Multimedia- & E-Learning Services (MELS),<br>
* University of Zurich, Switzerland.
* <hr>
* <a href="http://www.openolat.org">
* OpenOLAT - Online Learning and Training</a><br>
* This file has been modified by the OpenOLAT community. Changes are licensed
* under the Apache 2.0 license as the original file.
*/
package org.olat.course.statistic;

import java.util.Iterator;
import java.util.List;

import org.olat.core.logging.OLog;
import org.olat.core.logging.Tracing;
import org.olat.core.manager.BasicManager;
import org.olat.properties.Property;
import org.olat.properties.PropertyManager;

/**
 * Default implementation for LogginVersionManager - stores the version in 
 * the properties table using the following fields:
 * <ul>
 *  <li>category is set to LOGGING_PROPERTIES_CATEGORY</li>
 *  <li>name is set to LOGGING_VERSION_PROPERTY_NAME</li>
 *  <li>stringValue is set to String.valueOf(version)</li>
 *  <li>longValue is set to the unix-millis-time</li>
 * </ul>
 * <P>
 * Initial Date:  04.03.2010 <br>
 * @author Stefan
 */
public class LoggingVersionManagerImpl extends BasicManager implements LoggingVersionManager {

	/** the logging object used in this class **/
	private static final OLog log_ = Tracing.createLoggerFor(LoggingVersionManagerImpl.class);

	/** the category used for logging properties (in the o_properties table) **/
	private static final String LOGGING_PROPERTIES_CATEGORY = "LOGGING_PROPERTIES";

	/** the name used for logging version property (in the o_properties table) **/
	private static final String LOGGING_VERSION_PROPERTY_NAME = "LOGGING_VERSION";
	
	@Override
	public void setLoggingVersionStartingNow(int version) {
		setLoggingVersion(version, System.currentTimeMillis());
	}
	
	@Override
	public void setLoggingVersion(int version, long startingTimeMillis) {
		if (version<=0) {
			throw new IllegalArgumentException("version must be > 1");
		}
		if (startingTimeMillis<0) {
			throw new IllegalArgumentException("startingTimeMillis must be >= 0");
		}
		PropertyManager pm = PropertyManager.getInstance();
		List properties = pm.findProperties(null, null, null, LOGGING_PROPERTIES_CATEGORY, LOGGING_VERSION_PROPERTY_NAME);
		if (properties!=null && properties.size()>0) {
			// when there are already versions defined, lets see if the one which should be set now is already defined
			for (Iterator it = properties.iterator(); it.hasNext();) {
				Property property = (Property) it.next();
				if (property.getStringValue().equals(String.valueOf(version))) {
					// yes, the version is already set... overwrite this property then
					log_.info("setLoggingVersion: overwriting existing version property for version="+version+" from starttime "+property.getLongValue()+" to "+startingTimeMillis);
					property.setLongValue(startingTimeMillis);
					pm.saveProperty(property);
					return;
				}
			}
		}
		
		log_.info("setLoggingVersion: setting version property for version="+version+" to "+startingTimeMillis);
		Property newp = pm.createPropertyInstance(null, null, null, LOGGING_PROPERTIES_CATEGORY, LOGGING_VERSION_PROPERTY_NAME, null, startingTimeMillis, String.valueOf(version), null);
		pm.saveProperty(newp);
		
	}
	
	@Override
	public long getStartingTimeForVersion(int version) {
		PropertyManager pm = PropertyManager.getInstance();
		List properties = pm.findProperties(null, null, null, LOGGING_PROPERTIES_CATEGORY, LOGGING_VERSION_PROPERTY_NAME);
		if (properties!=null && properties.size()>0) {
			// when there are already versions defined, lets see if the one which should be set now is already defined
			for (Iterator it = properties.iterator(); it.hasNext();) {
				Property property = (Property) it.next();
				if (property.getStringValue().equals(String.valueOf(version))) {
					return property.getLongValue();
				}
			}
		}
		return -1;
	}

}
