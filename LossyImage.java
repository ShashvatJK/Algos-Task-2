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
import java.io.*;
public class LossyImage {
    
    public static void printArray(int[][] array,int edge,int count){
        System.out.println("Image "+count+":");
        for(int i=0;i<edge;i++){
            for(int j=0;j<edge;j++){
                System.out.print(array[i][j]+" ");
            }
            System.out.println("");
        }
       System.out.println(""); 
    }
    public static void copyArray(int[][] source_array,int[][] dest_array,int start_row_index,int end_row_index,int start_col_index,int end_col_index ){
        for(int i=start_row_index;i<=end_row_index;i++){
            for(int j=start_col_index;j<=end_col_index;j++){
                dest_array[i][j]=source_array[i-start_row_index][j-start_col_index];
            }
        }
    }
    public static int ones(int[][] array){
        int one=0;
        for(int i=0;i<array.length;i++){
            for(int j=0;j<array.length;j++){
                if(array[i][j]==1){
                        one++;
                }
            }
        }
        return one;
    }
    public static int zeros(int[][] array){
        int zero=0;
        for(int i=0;i<array.length;i++){
            for(int j=0;j<array.length;j++){
                if(array[i][j]==0){
                        zero++;
                }
            }
        }
        return zero;
    }
    public static int[][] divideAndRule(int[][] array,int start_row_index,int end_row_index,int start_col_index,int end_col_index){
        int temp[][]=new int[end_row_index-start_row_index+1][end_col_index-start_col_index+1];
        for(int i=start_row_index;i<=end_row_index;i++){
            for(int j=start_col_index;j<=end_col_index;j++){
                temp[i-start_row_index][j-start_col_index]=array[i][j];
            }
        }
        return temp;
    }
    public static int[][] retrieveImage(int[][] image,int lossy_conversion_percent,int count){
        int lossy_image[][]=new int[image.length][image.length];
        if(ones(image)*100/(ones(image)+zeros(image)) < lossy_conversion_percent && zeros(image)*100/(ones(image)+zeros(image)) < lossy_conversion_percent && image.length>=2){
            //quad2
            copyArray(
                    retrieveImage( 
                            divideAndRule(image, 0, (image.length/4)-1, 0, (image.length/4)-1), lossy_conversion_percent, count
                    ),image, 0, (image.length/4)-1, 0, (image.length/4)-1 
            );
            //quad1
            copyArray(
                    retrieveImage( 
                            divideAndRule(image, image.length/4 , image.length-1, 0, (image.length/4)-1), lossy_conversion_percent, count
                    ),image, image.length/4 , image.length-1, 0, (image.length/4)-1
            );
            //quad3
            copyArray(
                    retrieveImage( 
                            divideAndRule(image, 0, (image.length/4)-1, image.length/4, image.length-1), lossy_conversion_percent, count
                    ),image, 0, (image.length/4)-1, image.length/4, image.length-1 
            );
            //quad4
            copyArray(
                    retrieveImage( 
                            divideAndRule(image, image.length/4 , image.length-1, image.length/4 , image.length-1), lossy_conversion_percent, count
                    ),image, image.length/4 , image.length-1, image.length/4 , image.length-1
            );         
        }
        System.arraycopy(image, 0,lossy_image, 0, image.length);        
        return lossy_image;
    }
    
    public static void main(String args[]){ 
        Scanner scan= new Scanner(System.in);
        int grid_edge_length=0,lossy_conversion_percent=0,one=0,zero=0;
        int count=0;
        while(!scan.hasNext("0")){
            count++;
            grid_edge_length=scan.nextInt();
            lossy_conversion_percent=scan.nextInt();
            int input_grid[][]=new int[grid_edge_length][grid_edge_length];
            int lossy_grid[][]=new int[grid_edge_length][grid_edge_length];
            for(int i=0;i<grid_edge_length;i++){
                for(int j=0;j<grid_edge_length;j++){
                    input_grid[i][j]=scan.nextInt();
                }
            }
        retrieveImage(input_grid,lossy_conversion_percent,count);
        printArray(lossy_grid,grid_edge_length,count); 
        }
        scan.close();
    }
    
    
}
