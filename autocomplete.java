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
    
    }
}

class Trie_Node{
int wordLength;
boolean endOfWord;
char text;
LinkedList<Trie_Node> childrenList;

    public Trie_Node(char ch) {
        this.wordLength=0;
        this.endOfWord=false;
        childrenList=new LinkedList<Trie_Node>();
        text=ch;    
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
    return null;  
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
                return true;
            }
        }
    return false;   
    }
}