/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package btree;

/**
 *
 * @author User
 */
public class Test {
    
    public static void main(String args[])
    {
        BTree b1 = new BTree(5);
        b1.insert(3);
        b1.insert(5);
        System.out.println(b1.root.keys[0]);
        System.out.println(b1.root.keys[1]);
        
        
    }
    
    
}


