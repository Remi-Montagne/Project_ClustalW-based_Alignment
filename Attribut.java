import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.text.*;
import javax.swing.text.LayeredHighlighter.LayerPainter;
import javax.swing.text.DefaultHighlighter.DefaultHighlightPainter;
import java.util.*;

public class StyleAffichage extends SimpleAttributeSet {
	public StyleAffichage() {
		super();
	}
	
	public StyleAffichage(String style, int taillePolice, Color c) {
		super();
		StyleConstants.setFontFamily(this,style);
		StyleConstants.setFontSize(this,taillePolice);
		StyleConstants.setBackground(this,c);
	}


} 

