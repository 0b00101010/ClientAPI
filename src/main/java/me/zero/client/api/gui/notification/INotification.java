/*
 * Copyright 2017 ZeroMemes
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.zero.client.api.gui.notification;

/**
 * Base for Notifications
 *
 * @author Brady
 * @since 1/6/2017 12:00 PM
 */
interface INotification {

	/**
	 * @return The Header of the Notificaton
	 */
	String getHeader();

	/**
	 * @return The Subtext of the Notification
	 */
	String getSubtext();

	/**
	 * @return Returns the time in milliseconds when this Notification was created
	 */
	long getStart();

	/**
	 * @return Returns the time in milliseconds for the fade in and out
	 */
	long getFade();

	/**
	 * @return Returns the time in milliseconds that this notification should be displayed for
	 */
	long getDisplayTime();
}
