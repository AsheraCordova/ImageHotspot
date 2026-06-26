//start - license
/*
 * Copyright (c) 2025 Ashera Cordova
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 */
//end - license
package com.ashera.imagehotspot;

import java.util.Map;

import com.ashera.widget.EventExpressionParser;
import com.ashera.widget.IWidget;

public class HotspotEventCommand implements com.ashera.widget.EventCommand{
	@Override
	public Object executeCommand(IWidget widget, Map<String, Object> eventObject, Object... params) {
		String event = (String) eventObject.get(EventExpressionParser.KEY_SCRIPT_NAME);
		switch (event) {
			case "onTouch": {
				widget.getFragment().getEventBus().notifyObservers(widget.getId() + "OnTouch", eventObject);
				break;
			}
		}

		return null;
	}


}
