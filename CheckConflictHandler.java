/**
 * @(#)CheckConflictHandler.java
 *
 * Copyright: Copyright (c) 2003,2004 Carnegie Mellon University
 *
 */

import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * "List all students" command event handler.
 */
public class CheckConflictHandler extends CommandEventHandler {


    public static  boolean conflicted = false;
    /**
     * Construct "List all students" command event handler.
     *
     * @param objDataBase reference to the database object
     * @param iCommandEvCode command event code to receive the commands to process
     * @param iOutputEvCode output event code to send the command processing result
     */
    public CheckConflictHandler(DataBase objDataBase, int iCommandEvCode, int iOutputEvCode) {
        super(objDataBase, iCommandEvCode, iOutputEvCode);
    }

    /**
     * Process "List all students" command event.
     *
     * @param param a string parameter for command
     * @return a string result of command processing
     */
    protected String execute(String param) {
        StringTokenizer objTokenizer = new StringTokenizer(param);
        String sSID     = objTokenizer.nextToken();
        String sCID     = objTokenizer.nextToken();
        String sSection = objTokenizer.nextToken();

        // Get the student and course records.
        Student objStudent = this.objDataBase.getStudentRecord(sSID);
        Course objCourse = this.objDataBase.getCourseRecord(sCID, sSection);
        if (objStudent == null) {
            return "Invalid student ID";
        }
        if (objCourse == null) {
            return "Invalid course ID or course section";
        }

        // Check if the given course conflicts with any of the courses the student has registered.
        ArrayList vCourse = objStudent.getRegisteredCourses();
        for (int i=0; i<vCourse.size(); i++) {
            if (((Course) vCourse.get(i)).conflicts(objCourse)) {
                conflicted = true;
                return "Registration conflicts";
            }
        }
//        if(objCourse.vRegistered.size() >= overbookedThreshold){
//            return sCID + " is overbooked";
//        }
        conflicted = false;
        return "ok";
    }

    public static boolean getConflicted(){
        return conflicted;
    }
}