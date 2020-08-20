/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package map_application;

import com.bbn.openmap.layer.OMGraphicHandlerLayer;
import com.bbn.openmap.omGraphics.OMGraphicConstants;
import com.bbn.openmap.omGraphics.OMGraphicList;
import com.bbn.openmap.omGraphics.OMPoint;
import com.bbn.openmap.omGraphics.OMScalingIcon;
import com.bbn.openmap.omGraphics.OMTextLabeler;
import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author madha
 */
public class RecordPointLayer extends OMGraphicHandlerLayer{
    private OMGraphicList recordPoints = new OMGraphicList();
    private OMGraphicList Marker = new OMGraphicList();
    
    public RecordPointLayer(){
        
    }
    
    @Override
    public synchronized OMGraphicList prepare(){
//        if(getRecordPoints() != null){
            OMGraphicList ret = new OMGraphicList(getRecordPoints());
            ret.generate(getProjection());
//            return ret;
//        }
//        else{
//            OMGraphicList ret = new OMGraphicList(getMarker());
//            ret.generate(getProjection());
            return ret;
//        }
    }
    
    public void addRecordPoint(double lat, double lng, int width, String Label, Color color){
        try{
            OMPoint point = new OMPoint(lat,lng,width);
            point.setFillPaint(color);
//            point.putAttribute(OMGraphicConstants.LABEL, new OMTextLabeler(Label));
            point.generate(getProjection());
            getRecordPoints().add(point);
            doPrepare();
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        } 
    }
    
    public OMPoint addLabel(double lat, double lng, String Label){
        OMPoint point = new OMPoint(lat,lng,0);
        try{
            point.putAttribute(OMGraphicConstants.LABEL, new OMTextLabeler(Label));
            point.generate(getProjection());
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        } 
        return point;
    }
    
    public void addMarker(double lat, double lng){
        try{
            OMScalingIcon icon = new OMScalingIcon(lat, lng, new ImageIcon("marker3.png"));
            icon.setHeight(1);
            getMarker().add(icon);
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public void p(){
        doPrepare();
    }
    
    public void clearMarker(){
        getMarker().clear();
        doPrepare();
    }
    
    public void clearRecordPoint(){
        getRecordPoints().clear();
        doPrepare();
    }

    public OMGraphicList getRecordPoints() {
        return recordPoints;
    }

    public void setRecordPoints(OMGraphicList recordPoints) {
        this.recordPoints = recordPoints;
    }

    public OMGraphicList getMarker() {
        return Marker;
    }

    public void setMarker(OMGraphicList Marker) {
        this.Marker = Marker;
    }
}
