
import java.io.*;
import java.util.*;
import org.jgrapht.graph.*;
import org.jgrapht.io.*;


public class WarmUp {

    public static void main(String[] args) throws FileNotFoundException, ImportException {
      
        //CML inputs
        Reader reader = new FileReader(args[0]);
        String v1 = args[1];
        String v2 = args[2];
        
        //Import&create Directed Graph from the dot file
        DefaultDirectedGraph <String, DefaultEdge> family_tree= new DefaultDirectedGraph<>(DefaultEdge.class);
        
        VertexProvider vertex_provider = (VertexProvider) (String id, Map attributes) -> id;
        EdgeProvider<String,DefaultEdge> edge_provider = (String from, String to, String label, Map<String, Attribute> attributes) -> family_tree.getEdgeFactory().createEdge(from, to);
        
        DOTImporter importer = new DOTImporter(vertex_provider,edge_provider);
        importer.importGraph(family_tree, reader);
        
     
        
        System.out.println(lca(v1,v2,family_tree));
        
         
      
         
    } 
    
    public static Set<String> lca( String v1, String v2, DefaultDirectedGraph g ){
       
        HashMap<String,Character> processedNodes=new HashMap();  
        Queue<String> toExpand = new LinkedList();
        Set<String> commonAncestor = new HashSet();
        
        toExpand.offer("V1");
        toExpand.offer(v1);
        toExpand.offer("V2");
        toExpand.offer(v2);
        toExpand.offer("Depth");
        
        while(toExpand.size()>2){
        
            char dic_value ='1';
            String head = toExpand.remove();
            
            while(!head.equals("Depth")){
            
                if (head.equals("V1")) {toExpand.offer(head);head = toExpand.remove(); dic_value ='1';continue;}
                if(head.equals("V2")) {toExpand.offer(head); head = toExpand.remove(); dic_value ='2';continue;}
                
               Set<DefaultEdge>  edges =g.incomingEdgesOf(head);
               Iterator<DefaultEdge> edges_itr = edges.iterator();
              
               while(edges_itr.hasNext()){
                    DefaultEdge E = edges_itr.next();
                    String v = (String)g.getEdgeSource(E);
                
                    if (processedNodes.get(v)==null) 
                     {
                        processedNodes.put(v,dic_value);
                        toExpand.offer(v);
                        }
                    else if (processedNodes.get(v)!=dic_value) commonAncestor.add(v);
                  
                    }
               
                head = toExpand.remove();
                
            }
                if(!commonAncestor.isEmpty())return commonAncestor;
                if(toExpand.size()>2) toExpand.offer("Depth");
                
                
            
            
            
        }
        
        return commonAncestor;
    }
    
    
}
