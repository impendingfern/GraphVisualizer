import com.mxgraph.analysis.StructuralException;
import com.mxgraph.analysis.mxAnalysisGraph;
import com.mxgraph.analysis.mxGraphStructure;
import com.mxgraph.layout.mxOrganicLayout;
import com.mxgraph.swing.handler.mxRubberband;
import javax.swing.*;
import java.awt.*;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxGraph;
import com.mxgraph.view.mxStylesheet;
import java.util.ArrayList;
import java.util.Hashtable;
import static javax.swing.BoxLayout.Y_AXIS;


public class Visualizer extends JFrame
{
    JPanel canvas;
    JPanel ltoolbar;
    JPanel rtoolbar;
    JPanel output;
    JMenu menu;
    JMenuBar menubar;
    JMenuItem menubutton1;
    JMenuItem menubutton2;
    mxGraph graph;
    mxAnalysisGraph aGraph;
    mxGraphStructure structGraph;
    Object parent;
    ArrayList<Object> vertices;
    ArrayList<Object> edges;
    mxOrganicLayout organic;
    int index = 0;
    int edgenum;
        
    public Visualizer(){
        super("Hello, World!");
        initComponents();
    }
    
    public void initComponents()
    {
        
	//Crea los paneles del frame
        JPanel canvas = new newPanel();
        JPanel toolbar = new JPanel();
        JPanel output = new JPanel();
        JPanel leftoutput = new JPanel();
        
        JButton circleVertex = new JButton();
        JTextArea textOut = new JTextArea(10, 75);
        
        //Crea grafo y grafo de analisis
        vertices = new ArrayList<Object>();
        edges = new ArrayList<Object>();
        graph = new mxGraph();
        aGraph = new mxAnalysisGraph();
        structGraph = new mxGraphStructure();
        
        aGraph.setGraph(graph);
        parent = graph.getDefaultParent();
        organic = new mxOrganicLayout(graph);//para implementar un layout organico
        
        //Crea MenuBar
        menu = new JMenu("Grafo");
        menubar = new JMenuBar();
        
        //Agrega items al menu
        menubutton1 = new JMenuItem("Mostrar propiedades");
        menubutton1.addActionListener(new java.awt.event.ActionListener(){
            public void actionPerformed(java.awt.event.ActionEvent e){
                
                textOut.setText("Numero de vertices: " + vertices.size() + "\n");
                textOut.append("Numero de aristas: " + edges.size() + "\n");
                
                if(structGraph.isConnected(aGraph) == true)
                    textOut.append("El grafo es conexo\n");
                else
                    textOut.append("El grafo es disconexo\n");
                try{
                    structGraph.regularity(aGraph);
                    textOut.append("El grafo es regular\n");
                }catch(StructuralException s){
                    textOut.append("El grafo no es regular\n");
                }
            }
        });
        menu.add(menubutton1);
        
        menubutton2 = new JMenuItem("Grafo Dirigido");
        menubutton2.addActionListener(new java.awt.event.ActionListener(){
            public void actionPerformed(java.awt.event.ActionEvent e){
                
            }
        });
        menu.add(menubutton2);
        //Agrega el menu creado a menubar
        menubar.add(menu);
       
        /*canvas.addMouseListener(new java.awt.event.MouseListener(){
              public void mousePressed(MouseEvent e) {
              }

              public void mouseReleased(MouseEvent e) {
              }

              public void mouseEntered(MouseEvent e) {
              }

              public void mouseExited(MouseEvent e) {
              }

              public void mouseClicked(MouseEvent e) {
                  int source = e.getButton();
                  if(source == e.BUTTON3){
                      pop.show(canvas, 50, 50);
                  }
               }
        });*/
        
        //Configura JTextArea
       textOut.setEditable(false);
        
        //Configura grafo
        graph.setAllowLoops(true); //Permitir lazos
        graph.setAllowDanglingEdges(false); //No permitir aristas sin conectar
        graph.setCellsResizable(false); //No permitir vertices redimensionables
        
        //Crea el estilo ROUNDED para aplicar a los vertices
        mxStylesheet stylesheet = graph.getStylesheet();
        Hashtable<String, Object> style = new Hashtable<String, Object>();
        style.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_ELLIPSE);
        style.put(mxConstants.STYLE_OPACITY, 65);
        style.put(mxConstants.STYLE_FONTCOLOR, "#ffe28a");
        style.put(mxConstants.STYLE_FILLCOLOR, "#fb2e01");
        style.put(mxConstants.STYLE_FONTSIZE, 14);
        style.put(mxConstants.STYLE_STROKECOLOR, "#");
        style.put(mxConstants.STYLE_STROKE_OPACITY, 100);
        stylesheet.putCellStyle("ELLIPSE", style);
       
        //Crear boton para agregar vertices
        circleVertex.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/dot_circle1600 copia.png"))); // NOI18N
        circleVertex.setFocusable(false);
        circleVertex.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
           
            graph.getModel().beginUpdate();
            try
            {
                Object v1 = graph.insertVertex(parent, null, index, 200, 150, 35, 35, "ELLIPSE");  
                vertices.add(graph.getChildVertices(parent));
                //edgenum = edgenum + ((mxCell)vertices.get(index)).getEdgeCount();
                index++;            

            }
            
            finally
            {
                graph.getModel().endUpdate();

            }

            mxGraphComponent graphComponent = new mxGraphComponent(graph);
            mxRubberband rubber = new mxRubberband(graphComponent);
            canvas.add(graphComponent);
            canvas.revalidate();
     
        }
        });
        
        toolbar.setLayout(new BoxLayout(toolbar, Y_AXIS));
        output.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        GridBagConstraints d = new GridBagConstraints();
      
        toolbar.add(circleVertex);
        output.add(leftoutput, c);
        output.add(textOut, d);
        
        
        //Agregar elementos al frame
        getContentPane().setLayout(new BorderLayout());
	getContentPane().add(canvas, BorderLayout.CENTER);
        getContentPane().add(toolbar, BorderLayout.LINE_START);
        getContentPane().add(output, BorderLayout.PAGE_END);
    }
    
    public class newPanel extends JPanel{
        public newPanel(){
            this.setLayout(new BorderLayout());
            this.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        }
    }
    public static void main(String[] args)
    {
	Visualizer frame = new Visualizer();
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.setSize(950, 543);
        frame.setJMenuBar(frame.menubar);
	frame.setVisible(true);

    }

}

