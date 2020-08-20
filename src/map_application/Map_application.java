/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package map_application;

import Panels.EastPanel;
import Panels.NorthPanel;
import Panels.WestPanel;
import com.bbn.openmap.LayerHandler;
import com.bbn.openmap.MapBean;
import com.bbn.openmap.MouseDelegator;
import com.bbn.openmap.MultipleSoloMapComponentException;
import com.bbn.openmap.event.OMMouseMode;
import com.bbn.openmap.gui.LayerControlButtonPanel;
import com.bbn.openmap.gui.LayersPanel;
import com.bbn.openmap.gui.MapPanel;
import com.bbn.openmap.gui.NavigatePanel;
import com.bbn.openmap.gui.OMGraphicDeleteTool;
import com.bbn.openmap.gui.OpenMapFrame;
import com.bbn.openmap.gui.OverlayMapPanel;
import com.bbn.openmap.gui.RotTool;
import com.bbn.openmap.gui.ScaleTextPanel;
import com.bbn.openmap.gui.ToolPanel;
import com.bbn.openmap.gui.ZoomPanel;
import com.bbn.openmap.gui.menu.MenuList;
import com.bbn.openmap.layer.GraticuleLayer;
import com.bbn.openmap.layer.shape.BufferedShapeLayer;
import com.bbn.openmap.layer.shape.ShapeLayer;
import com.bbn.openmap.proj.coords.LatLonPoint;
import com.bbn.openmap.tools.drawing.OMCircleLoader;
import com.bbn.openmap.tools.drawing.OMLineLoader;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Properties;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.border.LineBorder;
/**
 *
 * @author madha
 */
public class Map_application {
    
    private MapBean mapBean;
    private ResultSet rs;
    private Connection conn;
    private Double lat;
    private Double lng;
    private String label;
    private JButton sh = new JButton("");
    private NorthPanel np = new NorthPanel();
    private WestPanel wp = new WestPanel();
    private EastPanel ep = new EastPanel();
    private MapPanel mapPanel = new OverlayMapPanel();
    private JButton addLayer;
    
    public Map_application(){
        RecordPointLayer recordPointLayer;
        
        try{
            mapPanel.addMapComponent(new LayerHandler());
            mapPanel.addMapComponent(new MouseDelegator());
            mapPanel.addMapComponent(new OMMouseMode());
            
            ToolPanel toolPanel = new ToolPanel();
            FlowLayout flowLayout = new FlowLayout();
            toolPanel.setLayout(flowLayout);
            toolPanel.setBorder(new LineBorder(Color.BLACK));
            flowLayout.setAlignment(FlowLayout.LEFT);
            sh.setIcon(new ImageIcon("menu2.png"));
            sh.setBorder(javax.swing.BorderFactory.createEmptyBorder(2, 2, 2, 2));
            toolPanel.add(sh);
            
            mapPanel.addMapComponent(toolPanel);
            mapPanel.addMapComponent(new LayersPanel());
            mapPanel.addMapComponent(new LayerControlButtonPanel());
            mapPanel.addMapComponent(new ScaleTextPanel());
            mapPanel.addMapComponent(new NavigatePanel());
            mapPanel.addMapComponent(new ZoomPanel());
            mapPanel.addMapComponent(new RotTool());
            mapPanel.addMapComponent(new OMGraphicDeleteTool());
            mapPanel.addMapComponent(new OMLineLoader());
            mapPanel.addMapComponent(new OMCircleLoader());
            mapPanel.getMapBean().setBackground(new Color(125,169,250));
            
            MenuList menuList = new MenuList();
            Properties menuProps = new Properties();
            menuProps.put("menus", "fileMenu controlMenu navigateMenu layersMenu gotoMenu");
            menuProps.put("fileMenu.class", "com.bbn.openmap.gui.FileMenu");
            menuProps.put("controlMenu.class", "com.bbn.openmap.gui.ControlMenu");
            menuProps.put("toolMenu.class", "com.bbn.openmap.gui.menu.OMBasicMenu");
            menuProps.put("navigateMenu.class", "com.bbn.openmap.gui.NavigateMenu");
            menuProps.put("layersMenu.class", "com.bbn.openmap.gui.LayersMenu");
            menuProps.put("gotoMenu.class", "com.bbn.openmap.gui.GoToMenu");
            menuList.setProperties(menuProps);
            mapPanel.addMapComponent(menuList);
            
            ShapeLayer shapeLayer = new BufferedShapeLayer();
            
            Properties shapeLayerProps = new Properties();
            shapeLayerProps.put("prettyName", "Political Solid");
            shapeLayerProps.put("lineColor", "000000");
            shapeLayerProps.put("fillColor", "fff0db");
            shapeLayerProps.put("shapeFile", "INDIA.shp");
            shapeLayer.setProperties(shapeLayerProps);
            
            mapPanel.addMapComponent(shapeLayer);
            
            recordPointLayer = new RecordPointLayer();
//            recordPointLayer.setName("Schools");
//            
//            try{
//            conn = connect.ConnecterDb();
//            rs =conn.createStatement().executeQuery("Select * from schools");
//            while(rs.next()){
//                lat = rs.getDouble(16);
//                lng = rs.getDouble(17);
//                label = rs.getString(6);
//                recordPointLayer.addRecordPoint(lat, lng, 3, label);
//            }
//            
//            conn.close();
//            }
//            catch(Exception e){
//                JOptionPane.showMessageDialog(null, e);
//            }
            
            try{
                conn = connect.ConnecterDb();
                rs = conn.createStatement().executeQuery("select distinct District from schools");
                while (rs.next()){
                    wp.getDistrict().addItem(rs.getString(1));
                }
                rs = conn.createStatement().executeQuery("select distinct Sch_Type_Boy_Girls from schools");
                while (rs.next()){
                    wp.getSch_type1().addItem(rs.getString(1));
                }
                rs = conn.createStatement().executeQuery("select distinct SCH_Typ1 from schools");
                while (rs.next()){
                    wp.getSch_type2().addItem(rs.getString(1));
                }
                rs = conn.createStatement().executeQuery("select distinct Categogaryofcombinedscore from schools");
                while (rs.next()){
                    wp.getCategory().addItem(rs.getString(1));
                }
                conn.close();
            }catch(Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
            
            mapPanel.addMapComponent(recordPointLayer);
            mapPanel.addMapComponent(new GraticuleLayer());
            
            OpenMapFrame frame = new OpenMapFrame("Indian Map");
            frame.setSize(1920,1080);
            
            np.setLayout(new GridLayout(1, 1, 1, 1));
            np.add(toolPanel);
            wp.setBackground(new Color(56,55,54));
            ep.setBackground(new Color(56,55,54));
            
            mapPanel.addMapComponent(frame);
            
            sh.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(wp.isVisible()){
                        wp.setVisible(false);
                    }
                    else{
                        wp.setVisible(true);
                    }
                }
            });
            
            addLayer = wp.getAdd_another();
            addLayer.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    mapPanel.addMapComponent(wp.getNewLayer());
                }
            });
            
            frame.add(np, java.awt.BorderLayout.NORTH);
            frame.add(wp, java.awt.BorderLayout.WEST);
            frame.add(ep, java.awt.BorderLayout.EAST);
            
            mapPanel.getMapBean().addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                }

                @Override
                public void mousePressed(MouseEvent e) {
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                }

                @Override
                public void mouseExited(MouseEvent e) {
                }
            });
            
            mapPanel.getMapBean().addMouseMotionListener(new MouseMotionListener(){
                @Override
                public void mouseDragged(MouseEvent e) {
                }

                @Override
                public void mouseMoved(MouseEvent e) {
                }
                
            });
            
            frame.addWindowListener(new WindowAdapter(){
                public void windowClosing(WindowEvent e){
                    System.exit(0);
                }
            });
            
            frame.setVisible(true);
            
            mapBean = mapPanel.getMapBean();
            mapBean.setCenter(new LatLonPoint.Double(13.0827,80.2707));
            mapBean.setScale(10000000f);
        }
        catch(MultipleSoloMapComponentException msmce){
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        javax.swing.SwingUtilities.invokeLater(new Runnable(){
            public void run(){
                Map_application mapApplication = new Map_application();
            }
        });
    }
    
}
