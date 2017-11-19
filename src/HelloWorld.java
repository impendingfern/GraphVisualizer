import javax.swing.*;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;
import java.awt.BorderLayout;
import static javax.swing.BoxLayout.Y_AXIS;


public class HelloWorld extends JFrame
{
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
                
        JButton vertexButton = new JButton();
        JButton edgeButton = new JButton();
                
        vertexButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/dot_circle1600 copia.png"))); // NOI18N
        vertexButton.setFocusable(false);
        //vertexButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        //vertexButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        //vertexButton.addActionListener(new java.awt.event.ActionListener() {
        //public void actionPerformed(java.awt.event.ActionEvent evt) {
            // jButton1ActionPerformed(evt);
        //}
        //});
                
       edgeButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/line-icon-61713-3 copia.png"))); // NOI18N
       edgeButton.setFocusable(false);
       edgeButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
       edgeButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
       //edgeButton.addActionListener(new java.awt.event.ActionListener() {
       //public void actionPerformed(java.awt.event.ActionEvent evt) {
            //  jButton2ActionPerformed(evt);
        //}
        //});
        canvas.setBackground(new java.awt.Color(255, 255, 255));
        canvas.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        
        rtoolbar.setSize(200, 50);
        rtoolbar.setLayout(new BoxLayout(rtoolbar, Y_AXIS));
        rtoolbar.add(vertexButton);
        rtoolbar.add(edgeButton);
                
        getContentPane().setLayout(new BorderLayout());
	getContentPane().add(canvas, BorderLayout.CENTER);
        getContentPane().add(ltoolbar, BorderLayout.LINE_START);
        getContentPane().add(rtoolbar, BorderLayout.LINE_END);
        getContentPane().add(output, BorderLayout.PAGE_END);
    }

    public class newPanel extends JPanel{
        public newPanel(){
            mxGraph graph = new mxGraph();
            Object parent = graph.getDefaultParent();

            graph.getModel().beginUpdate();
            try
            {
                Object v1 = graph.insertVertex(parent, null, "Hello", 200, 150, 80, 30);
                Object v2 = graph.insertVertex(parent, null, "World!", 340, 250, 80, 30);
                graph.insertEdge(parent, null, "Edge", v1, v2);
            }
            finally
            {
                graph.getModel().endUpdate();
            }

            mxGraphComponent graphComponent = new mxGraphComponent(graph);
            this.setLayout(new BorderLayout());
            this.add(graphComponent, BorderLayout.CENTER);
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

