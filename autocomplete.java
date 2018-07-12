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
public class autocomplete {
    public static void main(String[] args){
        Trie t= new Trie();
        Scanner scan = new Scanner(System.in);
        int num1=scan.nextInt();
        for(int i=0;i<num1;i++){
            String temp=scan.nextLine();
            t.add(temp);
            t.find(temp);
        }
        int num2=scan.nextInt();
        for(int i=0;i<num2;i++){
            String temp=scan.nextLine();
            switch(temp.substring(0, 3)){
                case "add":
                    t.add(temp.substring(4));
                    break;
                case "rem":
                    t.remove(temp.substring(7));
                    break;
                case "que":
                    t.query(temp.substring(6));
                    break;
                case "rev":
                    try{
                        t.revert(Integer.parseInt(temp.substring(7)));
                    }catch(NumberFormatException e){
                        System.out.println("ERROR!\n'revert' is not followed by a number.");
                    }
                    break;
                default: System.out.println("Incorrect Query.");   
            }
        }
    }
}

class Trie_Node{
int wordLength;
int occurences;
boolean endOfWord;
char text;
LinkedList<Trie_Node> childrenList;

    public Trie_Node(char ch) {
        this.wordLength=0;
        this.endOfWord=false;
        this.childrenList=new LinkedList<Trie_Node>();
        this.text=ch;
        this.occurences=0;
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
 
class Trie{
    Trie_Node root;
    public Trie(){
        root= new Trie_Node(' ');
    }
    
    public String query(String s){
        Trie_Node currentNode=root;
        for(char ch : s.toCharArray()){
            if(currentNode.getSubNode(ch)!=null){
                System.out.print(String.valueOf(currentNode.childrenList.toArray()));
                currentNode=currentNode.getSubNode(ch);
            }
            if(currentNode.endOfWord==true){
                
                return null;
            }
        }
       
        
    return "No strings begin with '"+s+"'";  
    }
    
    public void add(String s){
        if(find(s)==true){
            System.out.println("String "+s+" exists.");
            return;
        }
        Trie_Node currentNode=root;
        for(char ch : s.toCharArray()){
            if(currentNode.getSubNode(ch)==null){
                currentNode.childrenList.add(new Trie_Node(ch));
            }
            currentNode=currentNode.getSubNode(ch);            
            currentNode.wordLength++;
        }
        currentNode.endOfWord=true;
    }
    
    public void remove(String s){
        if(find(s)==false){
            System.out.println("String "+s+" does not exist.");
            return;
        }
        Trie_Node currentNode=root;
        for(char ch : s.toCharArray()){
            if(currentNode.wordLength==1){
                currentNode.childrenList.remove(currentNode.getSubNode(ch));
                return;
            }else{
                currentNode.wordLength--;
                currentNode=currentNode.getSubNode(ch);
            }
        }
        currentNode.endOfWord=false;
    }
    
    public boolean find(String s){
        Trie_Node currentNode=root;
        for(char ch : s.toCharArray()){
            if(currentNode.getSubNode(ch)!=null){
                currentNode=currentNode.getSubNode(ch);
            }
            if(currentNode.endOfWord==true){
                currentNode.occurences++;
                return true;
            }
        }
    return false;   
    }
    
    public void revert(int num){
        
    }
}