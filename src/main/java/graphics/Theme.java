/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphics;

import java.awt.Color;

/**
 *
 * @author desharnc27
 */
public class Theme {

    private Color txtContColor;
    private Color zoomPanColor;
    private Color bckgrContColor;
    private String name;
    public static Color parseStrToRGB(String s){
        int res=0;
        try{
            res=Integer.parseInt(s,16);
        }catch(NumberFormatException e){
            System.out.println("Warning: "+s+" is not a valid color hexacode, I'll assume 0");
        }
        return new Color(res);
    }
    public Theme(String [] strArr,String name){
        txtContColor = parseStrToRGB(strArr[0]);
        zoomPanColor = parseStrToRGB(strArr[1]);
        bckgrContColor = parseStrToRGB(strArr[2]);
        this.name=name;
    }
    /*public Theme(Color txt, Color zoomPan, Color bckgr) {
        txtContColor = txt;
        zoomPanColor = zoomPan;
        bckgrContColor = bckgr;
    }*/

    public Color txtContColor() {
        return txtContColor;
    }

    public Color zoomPanColor() {
        return zoomPanColor;
    }

    public Color bckgrContColor() {
        return bckgrContColor;
    }

    public String toString() {
        return 
                (name + ","
                + txtContColor.getRGB() + "," 
                + zoomPanColor.getRGB() + ","
                + bckgrContColor.getRGB()+"");
    }
    public String name(){
        return name;
    }
}
