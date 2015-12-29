package com.SmithsModding.SmithsCore.Util.Common;

import java.util.HashMap;

/**
 * Created by marcf on 12/28/2015.
 */
public class InstanceVariableManager
{
    private static final HashMap<String, Object> variables = new HashMap<String, Object>();

    /**
     * Method to set the state of a variable.
     * This state is hold-on until the currently running instance is closed. Then all variables are reset.
     *
     * @param variableID The ID of the variable that should be set.
     * @param variableState The state that should be linked to the given ID.
     */
    public static void setVariable(String variableID, Object variableState)
    {
        variables.put(variableID, variableState);
    }

    /**
     * Method to get the state of a variable.
     *
     * @param variableID The ID of a variable that should be retrieved.
     *
     * @return The state of the variable that should be retrieved, or null if not set.
     */
    public static Object getVariable(String variableID)
    {
        return variables.get(variableID);
    }

}
