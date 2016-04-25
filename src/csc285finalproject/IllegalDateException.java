/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csc285finalproject;

/**
 *
 * Exception to be thrown when an invalid date attempted to be accessed or created
 * <p>
 * @author colbysadams
 */
public class IllegalDateException extends Exception
{
    public IllegalDateException(String message)
    {
        super(message);
    }
}