import com.mxgraph.shape.mxStencil;
import com.mxgraph.shape.mxStencilShape;
import javax.swing.*;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxGraph;
import com.mxgraph.view.mxStylesheet;
import java.awt.BorderLayout;
import java.util.Hashtable;
import static javax.swing.BoxLayout.Y_AXIS;
import org.w3c.dom.Element;


public class HelloWorld extends JFrame
{
    JPanel canvas;
    JPanel ltoolbar;
    JPanel rtoolbar;
    JPanel output;
    mxGraph graph = new mxGraph();
    Object parent = graph.getDefaultParent();
        
    public HelloWorld(){
        super("Hello, World!");
        initComponents();
    }
    
    public void initComponents()
    {

	JPanel canvas = new newPanel();
        JPanel ltoolbar = new JPanel();
        JPanel rtoolbar = new JPanel();
        JPanel output = new JPanel();
                
        JButton circleVertex = new JButton();
        JButton squareVertex = new JButton();
                
        mxStylesheet stylesheet = graph.getStylesheet();
        Hashtable<String, Object> style = new Hashtable<String, Object>();
        style.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_ELLIPSE);
        style.put(mxConstants.STYLE_OPACITY, 50);
        style.put(mxConstants.STYLE_FONTCOLOR, "#774400");
        stylesheet.putCellStyle("ELLIPSE", style);
        
        circleVertex.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/dot_circle1600 copia.png"))); // NOI18N
        circleVertex.setFocusable(false);
        //vertexButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        //vertexButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        circleVertex.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
           
            graph.getModel().beginUpdate();
            try
            {
                Object v1 = graph.insertVertex(parent, null, null, 200, 150, 80, 30, "ELLIPSE");
                
            }
            
            finally
            {
                graph.getModel().endUpdate();
            }

            mxGraphComponent graphComponent = new mxGraphComponent(graph);
            
            canvas.add(graphComponent);
            canvas.revalidate();
        }
        });
                
       squareVertex.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/vector-path-square copia.png"))); // NOI18N
       squareVertex.setFocusable(false);
       squareVertex.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
       squareVertex.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
       squareVertex.addActionListener(new java.awt.event.ActionListener() {
       public void actionPerformed(java.awt.event.ActionEvent evt) {
           graph.getModel().beginUpdate();
            try
            {
                Object v1 = graph.insertVertex(parent, null, null, 200, 150, 80, 30);
                
            }
            
            finally
            {
                graph.getModel().endUpdate();
            }

            mxGraphComponent graphComponent = new mxGraphComponent(graph);
            
            canvas.add(graphComponent);
            canvas.revalidate();
        }
        });
        
        
        
        rtoolbar.setLayout(new BoxLayout(rtoolbar, Y_AXIS));
        rtoolbar.add(circleVertex);
        rtoolbar.add(squareVertex);
                
        getContentPane().setLayout(new BorderLayout());
	getContentPane().add(canvas, BorderLayout.CENTER);
        getContentPane().add(ltoolbar, BorderLayout.LINE_START);
        getContentPane().add(rtoolbar, BorderLayout.LINE_END);
        getContentPane().add(output, BorderLayout.PAGE_END);
    }
    
    public class newPanel extends JPanel{
        public newPanel(){
            this.setLayout(new BorderLayout());
            this.setBackground(new java.awt.Color(255, 255, 255));
            this.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        }
    }
        
    public static void main(String[] args)
    {
	HelloWorld frame = new HelloWorld();
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.setSize(950, 543);
	frame.setVisible(true);
    }

}

