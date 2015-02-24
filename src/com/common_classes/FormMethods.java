package com.common_classes;

import java.util.Iterator;

import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

/**
 *	This class contains methods related with forms 
 */
public class FormMethods
{
	/**
	 * Takes the ID of the form element (component) and returns it
	 * This method can be used for sending error messages to given component
	 */
	public static String findElementId(String componentId)
	{
	    FacesContext context = FacesContext.getCurrentInstance();
	    UIViewRoot root = context.getViewRoot();

	    UIComponent c = findComponent(root, componentId);
	    return c.getClientId(context);
	 }
	
	  /**
	   * Finds component with the given id
	   */
	  private static UIComponent findComponent(UIComponent c, String id) {
	    if (id.equals(c.getId())) {
	      return c;
	    }
	    Iterator<UIComponent> kids = c.getFacetsAndChildren();
	    while (kids.hasNext()) {
	      UIComponent found = findComponent(kids.next(), id);
	      if (found != null) {
	        return found;
	      }
	    }
	    return null;
	  }
}
