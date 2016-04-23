/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csc285finalproject;

/**
 * 
 * the Subject interface for the Observer pattern
 *
 * @author colbysadams
 */
public interface Subject 
{
    
    /**
     *
     * @param o observer to be added
     */
    public void addObserver(Observer o);
    
    /**
     *
     * @param o observer to be deleted
     */
    public void deleteObserver(Observer o);
    
    /**
     * Lets all observers know when a change has been made
     *
     */
    public void notifyObservers();
}