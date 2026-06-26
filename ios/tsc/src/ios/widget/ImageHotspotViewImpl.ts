// start - imports

	
import CommandAttr from '../../widget/CommandAttr';
import IWidget from '../../widget/IWidget';
import ILayoutParam from '../../widget/ILayoutParam';
import {plainToClass, Type, Exclude, Expose, Transform} from "class-transformer";
import {Gravity} from '../../widget/TypeConstants';
import {ITranform, TransformerFactory} from '../../widget/TransformerFactory';
import {Event} from '../../app/Event';
import {MotionEvent} from '../../app/MotionEvent';
import {DragEvent} from '../../app/DragEvent';
import {KeyEvent} from '../../app/KeyEvent';
import { ScopedObject } from '../../app/ScopedObject';
import { Mixin, decorate } from 'ts-mixer';








// end - imports
import {ViewImpl} from './ViewImpl';
export abstract class ImageHotspotViewImpl<T> extends ViewImpl<T>{
	//start - body
	static initialize() {
    }	
	@decorate(Type(() => CommandAttr))
	@decorate(Expose({ name: "hotspots" }))
	hotspots!:CommandAttr<string>| undefined;
	@decorate(Type(() => CommandAttr))
	@decorate(Expose({ name: "onhotspotClick" }))
	onhotspotClick!:CommandAttr<string>| undefined;
	@decorate(Type(() => CommandAttr))
	@decorate(Expose({ name: "hotspotOverlayDrawable" }))
	hotspotOverlayDrawable!:CommandAttr<string>| undefined;
	@decorate(Type(() => CommandAttr))
	@decorate(Expose({ name: "hotspotOverlayDrawablesByType" }))
	hotspotOverlayDrawablesByType!:CommandAttr<string>| undefined;
	@decorate(Type(() => CommandAttr))
	@decorate(Expose({ name: "hotspotDetectOnlyOnce" }))
	hotspotDetectOnlyOnce!:CommandAttr<boolean>| undefined;

	@decorate(Exclude())
	protected thisPointer: T;	
	protected abstract getThisPointer(): T;
	reset() : T {	
		super.reset();
		this.hotspots = undefined;
		this.onhotspotClick = undefined;
		this.hotspotOverlayDrawable = undefined;
		this.hotspotOverlayDrawablesByType = undefined;
		this.hotspotDetectOnlyOnce = undefined;
		return this.thisPointer;
	}
	constructor(id: string, path: string[], event:  string) {
		super(id, path, event);
		this.thisPointer = this.getThisPointer();
	}
	

	public setHotspots(value : string) : T {
		this.resetIfRequired();
		if (this.hotspots == null || this.hotspots == undefined) {
			this.hotspots = new CommandAttr<string>();
		}
		
		this.hotspots.setSetter(true);
		this.hotspots.setValue(value);
		this.orderSet++;
		this.hotspots.setOrderSet(this.orderSet);
		return this.thisPointer;
	}
		

	public setOnhotspotClick(value : string) : T {
		this.resetIfRequired();
		if (this.onhotspotClick == null || this.onhotspotClick == undefined) {
			this.onhotspotClick = new CommandAttr<string>();
		}
		
		this.onhotspotClick.setSetter(true);
		this.onhotspotClick.setValue(value);
		this.orderSet++;
		this.onhotspotClick.setOrderSet(this.orderSet);
		return this.thisPointer;
	}
		

	public setHotspotOverlayDrawable(value : string) : T {
		this.resetIfRequired();
		if (this.hotspotOverlayDrawable == null || this.hotspotOverlayDrawable == undefined) {
			this.hotspotOverlayDrawable = new CommandAttr<string>();
		}
		
		this.hotspotOverlayDrawable.setSetter(true);
		this.hotspotOverlayDrawable.setValue(value);
		this.orderSet++;
		this.hotspotOverlayDrawable.setOrderSet(this.orderSet);
		return this.thisPointer;
	}
		

	public setHotspotOverlayDrawablesByType(value : string) : T {
		this.resetIfRequired();
		if (this.hotspotOverlayDrawablesByType == null || this.hotspotOverlayDrawablesByType == undefined) {
			this.hotspotOverlayDrawablesByType = new CommandAttr<string>();
		}
		
		this.hotspotOverlayDrawablesByType.setSetter(true);
		this.hotspotOverlayDrawablesByType.setValue(value);
		this.orderSet++;
		this.hotspotOverlayDrawablesByType.setOrderSet(this.orderSet);
		return this.thisPointer;
	}
		

	public setHotspotDetectOnlyOnce(value : boolean) : T {
		this.resetIfRequired();
		if (this.hotspotDetectOnlyOnce == null || this.hotspotDetectOnlyOnce == undefined) {
			this.hotspotDetectOnlyOnce = new CommandAttr<boolean>();
		}
		
		this.hotspotDetectOnlyOnce.setSetter(true);
		this.hotspotDetectOnlyOnce.setValue(value);
		this.orderSet++;
		this.hotspotDetectOnlyOnce.setOrderSet(this.orderSet);
		return this.thisPointer;
	}
		
	//end - body

}
	
//start - staticinit

export class ImageHotspotView extends ImageHotspotViewImpl<ImageHotspotView> implements IWidget{
    getThisPointer(): ImageHotspotView {
        return this;
    }
    
   	public getClass() {
		return ImageHotspotView;
	}
	
   	constructor(id: string, path: string[], event: string) {
		super(id, path, event);	
	}
}

ImageHotspotViewImpl.initialize();
export interface OnHotspotClickEvent extends Event{
        //hotspot:Hotspot;

	        remainingHotSpots:number;
        //remainingHotSpotsByType:Map;


}

//end - staticinit
