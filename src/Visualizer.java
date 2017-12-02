import com.mxgraph.analysis.StructuralException;
import com.mxgraph.analysis.mxAnalysisGraph;
import com.mxgraph.analysis.mxGraphStructure;
import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.layout.mxOrganicLayout;
import com.mxgraph.swing.handler.mxRubberband;
import javax.swing.*;
import java.awt.*;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxGraph;
import com.mxgraph.view.mxStylesheet;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import static javax.swing.BoxLayout.Y_AXIS;


public class Visualizer extends JFrame
{
    JPanel canvas;
    JPanel ltoolbar;
    JPanel rtoolbar;
    JPanel output;
    JButton circleVertex;
    JButton deleteButton;
    JTextArea textOut;
    JMenu propmenu;
    JMenu layoutmenu;
    JMenuBar menubar;
    JMenuItem menubutton1;
    JMenuItem menubutton2;
    JMenuItem menubutton3;
    JMenuItem menubutton4;
    JMenuItem circlebutton;
    JMenuItem organicbutton;
    mxGraph graph;
    mxAnalysisGraph aGraph;
    mxGraphStructure structGraph;
    mxStylesheet stylesheet;
    Object parent;
    Object[] vertices;
    Object[] edges;
    mxOrganicLayout organic;
    mxCircleLayout circle;
    int index = 0;
    int edgenum;
    int x, y;
    boolean directed;
        
    public Visualizer(){
        super("Visualizador de Grafos");
        initComponents();
    }
    
    public void initComponents()
    {
        
	//Crea los paneles del frame
        JPanel canvas = new newPanel();
        JPanel toolbar = new JPanel();
        JPanel output = new JPanel();
        JPanel leftoutput = new JPanel();
        
        //Crea elementos del frame
        circleVertex = new JButton();
        deleteButton = new JButton();
        textOut = new JTextArea(10, 78);
        
        //Crea grafo y grafo de analisis
        graph = new mxGraph();
        aGraph = new mxAnalysisGraph();
        structGraph = new mxGraphStructure();
        
        //Configura grafo
        directed = false;
        aGraph.setGraph(graph);
        parent = graph.getDefaultParent();
        x = 30;
        y = 150;
        organic = new mxOrganicLayout(graph);//para implementar un layout organico
        circle = new mxCircleLayout(graph);
        
        //Crea MenuBar
        propmenu = new JMenu("Grafo");
        layoutmenu = new JMenu("Diseño");
        menubar = new JMenuBar();
        
        //Agrega items al propmenu
        menubutton1 = new JMenuItem("Mostrar propiedades");
        menubutton1.addActionListener(new java.awt.event.ActionListener(){
            public void actionPerformed(java.awt.event.ActionEvent e){
                
                vertices = graph.getChildVertices(parent);
                edges = graph.getChildEdges(parent);
                textOut.setText("Numero de vertices: " + vertices.length + "\n");
                textOut.append("Numero de aristas: " + edges.length + "\n");
                
                if(directed == false){
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
                     textOut.append("El grafo no es dirigido");
                }
                else
                    textOut.append("El grafo es dirigido");
                
                   
            }
        });
        propmenu.add(menubutton1);
        
        menubutton2 = new JMenuItem("Grafo Complementario");
        menubutton2.addActionListener(new java.awt.event.ActionListener(){
            public void actionPerformed(java.awt.event.ActionEvent e){
                structGraph.complementaryGraph(aGraph);
                edges = graph.getChildEdges(parent);
            }
        });
        propmenu.add(menubutton2);
        
        menubutton3 = new JMenuItem("Hacer Conexo");
        menubutton3.addActionListener(new java.awt.event.ActionListener(){
            public void actionPerformed(java.awt.event.ActionEvent e){
                structGraph.makeConnected(aGraph);
                edges = graph.getChildEdges(parent);
            }
        });
        propmenu.add(menubutton3);
        
        
        menubutton4 = new JMenuItem("Grafo dirigido");
        menubutton4.addActionListener(new java.awt.event.ActionListener(){
            public void actionPerformed(java.awt.event.ActionEvent e){
                if(directed == false){
                    graph.setCellStyle("DIGRAFO", edges);
                    directed = true;
                }
                
                else{
                    graph.setCellStyle("GRAFO", edges);
                    directed = false;
                }
            }
        });
        propmenu.add(menubutton4);
        
        //Agrega el propmenu creado a menubar
        menubar.add(propmenu);
        
        circlebutton = new JMenuItem("Distribucion circular");
        circlebutton.addActionListener(new java.awt.event.ActionListener(){
            public void actionPerformed(java.awt.event.ActionEvent e){
                circle.execute(parent);
            }
        });
        layoutmenu.add(circlebutton);
        
        /*organicbutton = new JMenuItem("Distribucion organica");
        organicbutton.addActionListener(new java.awt.event.ActionListener(){
            public void actionPerformed(java.awt.event.ActionEvent e){
                organic.execute(parent);
            }
        });
        layoutmenu.add(organicbutton);*/
        
        menubar.add(layoutmenu);
        
        //Configura JTextArea
       textOut.setEditable(false);
        
        //Configura grafo
        graph.setAllowLoops(true); //Permitir lazos
        graph.setAllowDanglingEdges(false); //No permitir aristas sin conectar
        graph.setCellsResizable(false); //No permitir vertices redimensionables
        
        //Crea el estilo ROUNDED para aplicar a los vertices
        stylesheet = graph.getStylesheet();
        Hashtable<String, Object> style = new Hashtable<String, Object>();
        Hashtable<String, Object> style2 = new Hashtable<String, Object>();
        Hashtable<String, Object> style3 = new Hashtable<String, Object>();
        stylesheet.getDefaultEdgeStyle().put(mxConstants.STYLE_ENDARROW, mxConstants.NONE);
        stylesheet.getDefaultVertexStyle().put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_ELLIPSE);

        style.put(mxConstants.STYLE_OPACITY, 65);
        style.put(mxConstants.STYLE_FONTCOLOR, "#ffe28a");
        style.put(mxConstants.STYLE_FILLCOLOR, "#fb2e01");
        style.put(mxConstants.STYLE_FONTSIZE, 14);
        style.put(mxConstants.STYLE_STROKECOLOR, "#");
        style.put(mxConstants.STYLE_STROKE_OPACITY, 100);
        stylesheet.putCellStyle("ELLIPSE", style);
        
        style2.put(mxConstants.STYLE_ENDARROW, mxConstants.ARROW_CLASSIC);
        style2.put(mxConstants.STYLE_OPACITY, 65);
        style2.put(mxConstants.STYLE_FONTCOLOR, "#ffe28a");
        style2.put(mxConstants.STYLE_FILLCOLOR, "#fb2e01");
        style2.put(mxConstants.STYLE_FONTSIZE, 14);
        style2.put(mxConstants.STYLE_STROKECOLOR, "#");
        stylesheet.putCellStyle("DIGRAFO", style2);
        
        style3.put(mxConstants.STYLE_ENDARROW, mxConstants.NONE);
        stylesheet.putCellStyle("GRAFO", style3);
        
        //Crear boton para agregar vertices
        circleVertex.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/add32.png"))); // NOI18N
        circleVertex.setFocusable(false);
        circleVertex.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
           
            graph.getModel().beginUpdate();
            try
            {
                Object v1 = graph.insertVertex(parent, null, index, x + (index * 32), y, 35, 35, "ELLIPSE");  
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
        
        deleteButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/remove32.png"))); // NOI18N
        deleteButton.setFocusable(false);
        deleteButton.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
           graph.removeCells();
        }
        });
        
        //configurar paneles
        output.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153), 1));
        toolbar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153), 1));
        canvas.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        
        toolbar.setBackground(new java.awt.Color(210, 212, 220));
        output.setBackground(new java.awt.Color(248, 248, 250));
        textOut.setForeground(new java.awt.Color(75, 75, 75));
        textOut.setBackground(new java.awt.Color(248, 248, 250));
        
        toolbar.setLayout(new BoxLayout(toolbar, Y_AXIS));
        output.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        GridBagConstraints d = new GridBagConstraints();
      
        toolbar.add(circleVertex);
        toolbar.add(deleteButton);
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
            //this.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
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

