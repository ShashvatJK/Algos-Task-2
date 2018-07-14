/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hp
 */
import java.util.*;

class CustomObject {
    int value1;
    String value2;

    CustomObject(int occurences, String text) {
        value1 = occurences;
        value2 = text;
    }
    
    public String getString(){
        return value2;
    }
    
    public int getInt(){
        return value1;
    }
    
    public void setInt(int i){
        value1=i;
    }
    
    
}

class Trie_Node{
int wordLength;
int index;
boolean endOfWord;
char text;
LinkedList<Trie_Node> childrenList;
//

    public Trie_Node(char ch) {
        wordLength=0;
        endOfWord=false;
        childrenList=new LinkedList<Trie_Node>();
        text=ch;
        index=-1;
    }
    
    public Trie_Node getSubNode(char ch){
        if(childrenList!=null){
            for(Trie_Node node : childrenList){
                if(node.text==ch){
                    return node;
                }
            }
        }
        return null;
    }
}
 
class Tries{
    Trie_Node root;
    List<CustomObject> content=new ArrayList<CustomObject>();
    public Tries(){
        root= new Trie_Node(' ');
    }
    
    public void getListOfStrings(String s){
        Object[][] list=new Object[2][content.size()];
        int k=0;
        for(int i=0;i<content.size();i++){
            String temp=content.get(i).getString();
            if(temp.substring(0,s.length()).equals(s)){
               list[0][k]=content.get(i).getInt();
               list[1][k]=temp;
               System.out.println(list[0][k]+","+list[1][k]);
               k++;
            }
        }    
    }
    
    public void sortByRow(Object arr[][],int row){
        Arrays.sort(arr[row]);
    }
    
    public void query(Trie_Node currentNode,String s){
        currentNode=root;
        int count=0;
        for(char ch : s.toCharArray()){
            Trie_Node child=currentNode.getSubNode(ch);
            if(count==s.length()){
                break;
            }
            if(child==null){
                System.out.println("No strings begin with '"+s+"'");
                return;
            }else{
                count++;
                currentNode=child;
            }           
        }         
        getListOfStrings(s);   
    }
    
    public void add(String s){      
        
        if(find(s,'a')==true){
            System.out.println("String "+s+" added again.");            
            return;
        }
        Trie_Node currentNode=root;
        //System.out.println(currentNode.childrenList.element());
        for(char ch : s.toCharArray()){
            Trie_Node child = currentNode.getSubNode(ch);
            if (child != null){
                currentNode = child;
            }
            else{
                 currentNode.childrenList.add(new Trie_Node(ch));
                 currentNode = currentNode.getSubNode(ch);
            }           
            currentNode.wordLength++;
        }
        //
        currentNode.endOfWord=true;
        CustomObject c=new CustomObject(1,s);
        content.add(c);
        currentNode.index=content.indexOf(c);
        System.out.println("Added String "+s);
    }
    
    public void remove(String s){
        if(find(s,'r')==false){
            System.out.println("String "+s+" does not exist.");
            return;
        }
        Trie_Node currentNode=root;
        
        for(char ch : s.toCharArray()){
             Trie_Node child= currentNode.getSubNode(ch);
            if(child.wordLength==1){
                CustomObject customobj=content.get(child.index);//Array index out of bounds exception  -1
                //check karke change kar
                int temp=customobj.getInt();
                customobj.setInt(--temp);
                child.wordLength=s.length();
                if(customobj.getInt()==0){
                    currentNode.childrenList.remove(child);
                    content.remove(currentNode.index);
                    currentNode.endOfWord=false; 
                }
                return;
            }else{
                child.wordLength--;
                currentNode=child;
            }
        }
        
        System.out.println("Removed String "+s);
    }
    
    public boolean find(String s, char c){
        Trie_Node currentNode=root;
        for(char ch : s.toCharArray()){
            if(currentNode.getSubNode(ch)==null){
                return false;
            }else{
                currentNode=currentNode.getSubNode(ch);
            }
        }
        if(currentNode.endOfWord==true){
            CustomObject customobj=content.get(currentNode.index);
            int temp=customobj.getInt();
            if(c=='a'){
                customobj.setInt(++temp);    
            }                     
            return true;
        }
    return false;   
    }
    
    public void revert(int num){
        
    }
}

public class autocomplete {
    public static void main(String[] args){
        Tries t= new Tries();
        Scanner scan = new Scanner(System.in);
        int num1=Integer.parseInt(scan.nextLine());
        //System.out.println("Enter the");
        int i1=0,i2=0;
        while(i1<num1){
            String temp=scan.nextLine();
            t.add(temp);
            //t.find(temp);
            i1++;
        }
        //System.out.println("\n");
        int num2=Integer.parseInt(scan.nextLine());
        while(i2<num2){
            String temp=scan.nextLine();
            //try{
                switch(temp.substring(0, 3)){
                    case "add":
                        t.add(temp.substring(4));
                        //System.out.println(t.find(temp.substring(4)));
                        break;
                    case "rem":
                        t.remove(temp.substring(7));
                        //System.out.println(t.find(temp.substring(7)));
                        break;
                    case "que":
                        t.query(t.root,temp.substring(6));
                        break;
                    case "rev":
                        try{
                            t.revert(Integer.parseInt(temp.substring(7)));
                        }catch(NumberFormatException e){
                            System.out.println("ERROR!\n'revert' is not followed by a number.");
                        }
                        break;
                    default: System.out.println("Incorrect Query.");   
               // }
           // }catch(Exception e){
            //    System.out.println("Incorrect Query");
            //    i2--;
            }
            i2++;
        }
    }
}


