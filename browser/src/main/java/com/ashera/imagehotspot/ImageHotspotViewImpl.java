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
//start - imports
import java.util.*;

import r.android.content.Context;
import r.android.os.Build;
import r.android.view.View;
import r.android.annotation.SuppressLint;
import r.android.annotation.SuppressLint;

import com.ashera.widget.*;
import com.ashera.converter.*;


import static com.ashera.widget.IWidget.*;
//end - imports

import r.android.graphics.drawable.Drawable;
import java.util.List;
import r.android.graphics.PointF;
import r.android.widget.ImageView;
import r.android.view.MotionEvent;
public class ImageHotspotViewImpl implements com.ashera.widget.IAttributable {
	// start - body
	public final static String LOCAL_NAME = "ImageHotspotView"; 
	private IWidget w;
	private java.util.Map<IWidget, IAttributable> instances = new java.util.WeakHashMap<>();
	private ImageHotspotViewImpl(IWidget widget) {
		this.w = widget;
	}
	
	public String getLocalName() {
		return LOCAL_NAME;
	}
	
	public ImageHotspotViewImpl() {
	}
	
	@Override
	public com.ashera.widget.IAttributable newInstance(IWidget widget) {
		return instances.computeIfAbsent(widget, w -> new ImageHotspotViewImpl(w));
	}
	
	
	@SuppressLint("NewApi")
	@Override
	public void loadAttributes(String localName) {

		WidgetFactory.registerAttribute(localName, new WidgetAttribute.Builder().withName("hotspots").withType("array").withArrayType("resourcestring"));
		WidgetFactory.registerAttribute(localName, new WidgetAttribute.Builder().withName("onhotspotClick").withType("string"));
		WidgetFactory.registerAttribute(localName, new WidgetAttribute.Builder().withName("hotspotOverlayDrawable").withType("string"));
		WidgetFactory.registerAttribute(localName, new WidgetAttribute.Builder().withName("hotspotOverlayDrawablesByType").withType("string"));
		WidgetFactory.registerAttribute(localName, new WidgetAttribute.Builder().withName("hotspotDetectOnlyOnce").withType("boolean"));
	}

	@SuppressLint("NewApi")
	@Override
	public void setAttribute(WidgetAttribute key, String strValue, Object objValue, ILifeCycleDecorator decorator) {
		View view = (View) w.asWidget();
		org.teavm.jso.dom.html.HTMLElement hTMLElement = (org.teavm.jso.dom.html.HTMLElement) w.asNativeWidget();
		switch (key.getAttributeName()) {
		case "hotspots": {


		 setHotSpots(w, objValue);



			}
			break;
		case "onhotspotClick": {


		setOnHotspotClick(w, strValue, objValue);



			}
			break;
		case "hotspotOverlayDrawable": {


		setHotspotOverlayDrawable(w, strValue, objValue, view);



			}
			break;
		case "hotspotOverlayDrawablesByType": {


		setHotspotOverlayDrawablesByType(w, strValue, objValue, view);



			}
			break;
		case "hotspotDetectOnlyOnce": {


		setHotspotDetectOnlyOnce(w, strValue, objValue, view);



			}
			break;
		default:
			break;
		}
	}
	
	@SuppressLint("NewApi")
	@Override
	public Object getAttribute(WidgetAttribute key, ILifeCycleDecorator decorator) {
		View view = (View) w.asWidget();
		org.teavm.jso.dom.html.HTMLElement hTMLElement = (org.teavm.jso.dom.html.HTMLElement) w.asNativeWidget();
		switch (key.getAttributeName()) {
		}
		return null;
	}
	
	

	private class Hotspot {
		String id;
		String type;
		float percX;
		boolean found;
		float percY;
		float percWidth;
		float percHeight;
		float imageWidth;
		float imageHeight;

		public boolean contains(float normalizedX, float normalizedY) {
			float imageX = normalizedX * imageWidth;
			float imageY = normalizedY * imageHeight;

			float left = percX * imageWidth;
			float top = percY * imageHeight;

			float hotspotWidth = percWidth * imageWidth;
			float hotspotHeight = percHeight * imageHeight;

			return imageX >= left && imageX <= left + hotspotWidth && imageY >= top && imageY <= top + hotspotHeight;
		}

		public String getType() {
			return type;
		}

	}

	
	private List<Hotspot> hotspots;

	private void setHotSpots(IWidget w, Object value) {
		hotspots = new ArrayList<>();

		List<Object> hotspotDefs = (List<Object>) value;

		for (Object hotspotDef : hotspotDefs) {
			Map<String, Object> params = com.ashera.model.ModelExpressionParser.parseSimpleCssExpression((String) hotspotDef);

			Hotspot hotspot = new Hotspot();

			hotspot.id = (String) params.get("id");
			hotspot.type = (String) params.get("type");
			hotspot.imageWidth = Float.parseFloat((String) params.get("imageWidth"));
			hotspot.imageHeight = Float.parseFloat((String) params.get("imageHeight"));
			hotspot.percX = Float.parseFloat((String) params.get("percX"));
			hotspot.percY = Float.parseFloat((String) params.get("percY"));
			hotspot.percWidth = Float.parseFloat((String) params.get("percWidth"));
			hotspot.percHeight = Float.parseFloat((String) params.get("percHeight"));

			hotspots.add(hotspot);
		}
		
		new r.android.os.Handler().post(() -> {
			showDebugHotspots((ImageView) w.asWidget());
		});
	}

	public static class ImageTransform {
		public float scaleX;
		public float scaleY;
		public float translateX;
		public float translateY;

		public int imageWidth;
		public int imageHeight;

		public PointF viewToNormalizedImage(float x, float y) {
			float imageX = (x - translateX) / scaleX;
			float imageY = (y - translateY) / scaleY;

			return new PointF(imageX / imageWidth, imageY / imageHeight);
		}
		
		public PointF imageToView(float normalisedX, float normalisedY) {
			float x = normalisedX * imageWidth;
			float y = normalisedY * imageHeight;
	        return new PointF(
	                x * scaleX + translateX,
	                y * scaleY + translateY);
	    }
	}

	private Hotspot findHotspot(ImageView imageView, float viewX, float viewY) {
		if (hotspots != null) {
			ImageTransform transform = fromImageView(imageView);
			if (transform != null) {
				PointF normalizedImagePoint = transform.viewToNormalizedImage(viewX, viewY);
	
				for (Hotspot hotspot : hotspots) {
					if (hotspot.contains(normalizedImagePoint.x, normalizedImagePoint.y)) {
						return hotspot;
					}
				}
			}
		}

		return null;
	}
	
	
	private void setOnHotspotClick(IWidget w, String strValue, Object objValue) {
		String type = w.getId() + "OnTouch";
		w.getFragment().getEventBus().off(type);
		w.getFragment().getEventBus().on(type, new com.ashera.widget.bus.EventBusHandler(type) {
			@Override
			protected void doPerform(Object payload) {
				Map<String, Object> map = PluginInvoker.getMap(PluginInvoker.getMap(payload).get("eventInfo"));
				int action = (Integer) map.get("action");
				if (action == MotionEvent.ACTION_UP) {
					int x = ((Number) map.get("x")).intValue();
					int y = ((Number) map.get("y")).intValue();
					Hotspot hotspot = findHotspot((ImageView) w.asWidget(), x, y);
					
					if (hotspot != null) {
						addOverlay(w, hotspot);
						hotspot.found = true;
						OnHotspotClickListener listener = new OnHotspotClickListener(w, (String) objValue);
						Map<String, Integer> remainingHotSpotsByType = getRemainingHotspotsByType();
						listener.onHotspotClick(hotspot, (int) hotspots.stream().filter((f) -> !f.found).count(), remainingHotSpotsByType);
						
						 if (hotspotDetectOnlyOnce) {
							 hotspots.remove(hotspot);
						 }
					}
				}
			}

			private Map<String, Integer> getRemainingHotspotsByType() {
				Map<String, Integer> remainingHotSpotsByType = new HashMap<>();

				for (Hotspot hotspot : hotspots) {
				    if (hotspot.found) {
				        continue;
				    }

				    String type = hotspot.getType();

				    Integer count = remainingHotSpotsByType.get(type);
				    if (count == null) {
				        count = 0;
				    }

				    remainingHotSpotsByType.put(type, count + 1);
				}
				return remainingHotSpotsByType;
			}

			private void addOverlay(IWidget w, Hotspot hotspot) {
				if (!hotspot.found && (hotspotOverlayDrawable != null || hotspotOverlayDrawablesByType != null)) {
					ImageView imageView = (ImageView) w.asWidget();
					ImageTransform transform = fromImageView(imageView);
					PointF topLeft = transform.imageToView(hotspot.percX, hotspot.percY);
					PointF bottomRight = transform.imageToView(hotspot.percX + hotspot.percWidth, 
							hotspot.percY + hotspot.percHeight);
					
					String overlayDrawable;
					if (hotspotOverlayDrawablesByType != null) {
						overlayDrawable = (String) hotspotOverlayDrawablesByType.get(hotspot.type);
					} else {
						overlayDrawable = hotspotOverlayDrawable;
					}
					
					if (overlayDrawable != null) {
						Drawable markerCopy = (Drawable) w.quickConvert(overlayDrawable, "drawable");
						setUseGc(markerCopy);
						if (markerCopy != null) {
							markerCopy.setBounds(
							        (int) topLeft.x,
							        (int) topLeft.y,
							        (int) bottomRight.x,
							        (int) bottomRight.y);
							imageView.getOverlay().add(markerCopy);
							requestLayout(imageView);
						}
					}
				}
			}
		});
	}

	interface ImageHotspotView {
		static interface OnHotspotClickListener {
			boolean onHotspotClick(Hotspot hotspot, int remainingHotSpots, Map remainingHotSpotsByType);
		}
	}
	
	private String hotspotOverlayDrawable;
	private void setHotspotOverlayDrawable(IWidget w2, String strValue, Object objValue, View view) {
		hotspotOverlayDrawable = (String) objValue;
	}
	
	private Map<String, Object> hotspotOverlayDrawablesByType;
	private void setHotspotOverlayDrawablesByType(IWidget w2, String strValue, Object objValue, View view) {
		hotspotOverlayDrawablesByType = com.ashera.model.ModelExpressionParser.parseSimpleCssExpression((String) objValue);
		
	}
	private boolean hotspotDetectOnlyOnce;
	private void setHotspotDetectOnlyOnce(IWidget w2, String strValue, Object objValue, View view) {
		hotspotDetectOnlyOnce = (boolean) objValue;
	}
	
	
	public static void addEventInfo(Map<String, Object> obj, Hotspot hotspot) {
		Map<String, Object> eventInfo = PluginInvoker.getJSONCompatMap();
		eventInfo.put("id", hotspot.id);
		eventInfo.put("type", hotspot.type);
		eventInfo.put("percX", hotspot.percX);
		eventInfo.put("percY", hotspot.percY);
		eventInfo.put("percWidth", hotspot.percWidth);
		eventInfo.put("percHeight", hotspot.percHeight);
		eventInfo.put("imageWidth", hotspot.imageWidth);
		eventInfo.put("imageHeight", hotspot.imageHeight);
		obj.put("hotspot", PluginInvoker.getNativeMap(eventInfo));
	}
	
	public static void addEventInfo(Map<String, Object> obj, Map remainingHotSpotsByType) {
		Map<String, Object> eventInfo = PluginInvoker.getJSONCompatMap();
		eventInfo.putAll(remainingHotSpotsByType);
		obj.put("remainingHotSpotsByType", PluginInvoker.getNativeMap(eventInfo));
	}



	
	private void showDebugHotspots(ImageView imageView) {
		if (hotspots == null) {
			return;
		}

		imageView.getOverlay().clear();

		ImageTransform transform = fromImageView(imageView);

		if (transform == null) {
			return;
		}

		for (Hotspot hotspot : hotspots) {

			PointF topLeft = transform.imageToView(hotspot.percX, hotspot.percY);
			PointF bottomRight = transform.imageToView(hotspot.percX + hotspot.percWidth,
					hotspot.percY + hotspot.percHeight);

			Drawable debugDrawable = createDebugDrawable(Math.round(topLeft.x), Math.round(topLeft.y),
					Math.round(bottomRight.x), Math.round(bottomRight.y));

			if (debugDrawable != null) {
				imageView.getOverlay().add(debugDrawable);
			}
		}
	}
	


	private Drawable createDebugDrawable(int left, int top, int right, int bottom) {
		return null;
	}

	private ImageTransform fromImageView(ImageView imageView) {
		com.ashera.model.RectM bounds = imageView.getImageBounds(imageView.getMeasuredWidth(), imageView.getMeasuredHeight());

	    if (bounds == null) {
	        return null;
	    }

	    ImageTransform transform = new ImageTransform();
	    transform.scaleX = (float) bounds.width / imageView.getDrawableWidth();
	    transform.scaleY = (float) bounds.height / imageView.getDrawableHeight();
	    transform.translateX = bounds.x;
	    transform.translateY = bounds.y;
	    
	    transform.imageWidth = imageView.getDrawableWidth();
		transform.imageHeight = imageView.getDrawableHeight();

	    return transform;
	}
	
	private void requestLayout(ImageView imageView) {
		imageView.requestLayout();
	}
	

	@SuppressLint("NewApi")
private static class OnHotspotClickListener implements ImageHotspotView.OnHotspotClickListener, com.ashera.widget.IListener{
private IWidget w; private View view; private String strValue; private String action;
public String getAction() {return action;}
public OnHotspotClickListener(IWidget w, String strValue)  {
this.w = w; this.strValue = strValue;
}
public OnHotspotClickListener(IWidget w, String strValue, String action)  {
this.w = w; this.strValue = strValue;this.action=action;
}
public boolean onHotspotClick(Hotspot hotspot, int remainingHotSpots, Map remainingHotSpotsByType){
    boolean result = true;
    
	if (action == null || action.equals("onHotspotClick")) {
		// populate the data from ui to pojo
		w.syncModelFromUiToPojo("onHotspotClick");
	    java.util.Map<String, Object> obj = getOnHotspotClickEventObj(hotspot,remainingHotSpots,remainingHotSpotsByType);
	    String commandName =  (String) obj.get(EventExpressionParser.KEY_COMMAND_NAME);
	    
	    // execute command based on command type
	    String commandType = (String)obj.get(EventExpressionParser.KEY_COMMAND_TYPE);
		switch (commandType) {
		case "+":
		    if (EventCommandFactory.hasCommand(commandName)) {
		    	 Object commandResult = EventCommandFactory.getCommand(commandName).executeCommand(w, obj, hotspot,remainingHotSpots,remainingHotSpotsByType);
		    	 if (commandResult != null) {
		    		 result = (boolean) commandResult;
		    	 }
		    }

			break;
		default:
			break;
		}
		
		if (obj.containsKey("refreshUiFromModel")) {
			Object widgets = obj.remove("refreshUiFromModel");
			com.ashera.layout.ViewImpl.refreshUiFromModel(w, widgets, true);
		}
		if (w.getModelUiToPojoEventIds() != null) {
			com.ashera.layout.ViewImpl.refreshUiFromModel(w, w.getModelUiToPojoEventIds(), true);
		}
		if (strValue != null && !strValue.isEmpty() && !strValue.trim().startsWith("+")) {
		    com.ashera.core.IActivity activity = (com.ashera.core.IActivity)w.getFragment().getRootActivity();
		    if (activity != null) {
		    	activity.sendEventMessage(obj);
		    }
		}
	}
    return result;
}//#####

public java.util.Map<String, Object> getOnHotspotClickEventObj(Hotspot hotspot,int remainingHotSpots,Map remainingHotSpotsByType) {
	java.util.Map<String, Object> obj = com.ashera.widget.PluginInvoker.getJSONCompatMap();
    obj.put("action", "action");
    obj.put("eventType", "hotspotclick");
    obj.put("fragmentId", w.getFragment().getFragmentId());
    obj.put("actionUrl", w.getFragment().getActionUrl());
    obj.put("namespace", w.getFragment().getNamespace());
    
    if (w.getComponentId() != null) {
    	obj.put("componentId", w.getComponentId());
    }
    
    PluginInvoker.putJSONSafeObjectIntoMap(obj, "id", w.getId());
     
        ImageHotspotViewImpl.addEventInfo(obj, hotspot);
        PluginInvoker.putJSONSafeObjectIntoMap(obj, "remainingHotSpots", remainingHotSpots);
        ImageHotspotViewImpl.addEventInfo(obj, remainingHotSpotsByType);
    
    // parse event info into the map
    EventExpressionParser.parseEventExpression(strValue, obj);
    
    // update model data into map
    w.updateModelToEventMap(obj, "onHotspotClick", (String)obj.get(EventExpressionParser.KEY_EVENT_ARGS));
    return obj;
}
}


	// end - body
	

	private void setUseGc(Drawable markerCopy) {
		
	}
}
