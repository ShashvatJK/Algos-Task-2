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

class Trie_Node{
int wordLength;
int occurences;
boolean endOfWord;
char text;
LinkedList<Trie_Node> childrenList;

    public Trie_Node(char ch) {
        wordLength=0;
        endOfWord=false;
        childrenList=new LinkedList<Trie_Node>();
        text=ch;
        occurences=0;
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
    char Alphabet[]= new char[53];
    public Tries(){
        root= new Trie_Node(' ');
        for(int i=0;i<53;i++){
            if(i<26){
                Alphabet[i]=(char) (97+i);
            }
            else 
                if(i>=26 && i<52){
                    Alphabet[i]=(char) (67+i-26);
                }else{
                    Alphabet[i]=' ';
                }
        }
    }
    
    public void query(Trie_Node currentNode,String s){
        currentNode=root;
        String str=s;
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
        for(char c: Alphabet){
            
            if(currentNode.getSubNode(c)!=null){
                str=str+c;
                query(currentNode.getSubNode(c),s);
                if(currentNode.getSubNode(c).endOfWord==true){
                    System.out.println(str);
                    str=s;  
                }
            }else{
                continue;
            }
        }
    }
    
    public void add(String s){        
        if(find(s)==true){
            System.out.println("String "+s+" exists.");
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
        System.out.println("Added String "+s);
    }
    
    public void remove(String s){
        if(find(s)==false){
            System.out.println("String "+s+" does not exist.");
            return;
        }
        Trie_Node currentNode=root;
        for(char ch : s.toCharArray()){
            Trie_Node child = currentNode.getSubNode(ch);
            if(child.wordLength==1){
                currentNode.childrenList.remove(child);
                return;
            }else{
                child.wordLength--;
                currentNode=child;
            }
        }
        currentNode.endOfWord=false;  
        System.out.println("Removed String "+s);
    }
    
    public boolean find(String s){
        Trie_Node currentNode=root;
        for(char ch : s.toCharArray()){
            if(currentNode.getSubNode(ch)==null){
                return false;
            }else{
                currentNode=currentNode.getSubNode(ch);
            }
        }
        if(currentNode.endOfWord==true){
            if(currentNode.wordLength==s.length()){
                currentNode.occurences++;
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
            t.find(temp);
            i1++;
        }
        //System.out.println("\n");
        int num2=Integer.parseInt(scan.nextLine());
        while(i2<num2){
            String temp=scan.nextLine();
            switch(temp.substring(0, 3)){
                case "add":
                    t.add(temp.substring(4));
                    System.out.println(t.find(temp.substring(4)));
                    break;
                case "rem":
                    t.remove(temp.substring(7));
                    System.out.println(t.find(temp.substring(7)));
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
            }
            i2++;
        }
    }
}


