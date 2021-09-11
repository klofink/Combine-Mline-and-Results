package com.jetbrains;
// debug Cancelled

import java.io.*;
import java.io.IOException;
import java.io.PrintWriter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Date;
import java.util.Collections;

import java.lang.StringBuilder;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.Instant;
import java.time.ZoneId;

public class Main {

    private static String getNameWin(String s) {
        //
        int sp1 = s.indexOf('.');
        //int sp2 = s.indexOf('.',sp1+1);
        //int sp3 = s.indexOf('.',sp2+1);
        if (sp1 < 0)
            sp1=s.length();

        String s2 = "";
        String wkS="";

        char[] wkC = new char[s.length()];
        s = s.replace(' ',',');

        s.getChars(0,s.length(),wkC,0);
        for(int i=3; i < sp1; i++){
            if(wkC[i] == ',' && i < (sp1-3) ){
                wkC[i]=' ';
            } // end if
        } // end for loop

        for(int i=0; i < wkC.length; i++) {
            s2 += wkC[i];
        }
        int hasC= s2.indexOf('(');
        int find2nd=s2.indexOf(',',3);
        if( hasC > 0){
            // name has Country in it
           wkS = s2.substring(0,hasC-1);
           s2 = wkS+s2.substring(find2nd);
;
        }

        return s2;


    } // end getNameWin

    public static ArrayList<String> getList( File path){

        ArrayList<String> list = new ArrayList<String>();
        try {
            Scanner scanML = new Scanner(path);

        while(scanML.hasNextLine()){
            list.add(scanML.nextLine());
        }


        scanML.close();


        } catch (IOException ex) {
//            Logger.getLogger(PDFBoxReadFromFile.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println(ex.getMessage());
        } //  end catch

        return list;

    } // end getML

    public static String getOdds(String s){
        String[][] oddConvert = new String[][]{{"2/1", "2.0"}, {"3/1", "3.0"}, {"4/1", "4.0"}, {"5/1", "5.0"}, {"6/1", "6.0"}, {"7/1", "7.0"}, {"8/1", "8.0"},{"9/1","9.0"},
                {"1/1", "1.0"}, {"2/5", ".40"}, {"1/2", ".50"}, {"3/5", ".60"}, {"4/5", ".80"}, {"6/5", "1.2"}, {"7/5", "1.4"}, {"8/5", "1.6"}, {"9/5", "1.8"},
                {"3/2", "1.5"}, {"5/2", "2.5"}, {"7/2", "3.5"}, {"9/2", "4.5"} };

        String wkS1="";
        String wkS="";
        String wkS2="";
        char s1C = s.charAt(0);
        // s should = ex. " 1/1" or "20/1"
        if(s1C == ' '){ // this means odds are less than 10-1
                      wkS2 = s.substring(1); // take space out
                for (int y = 0; y < 21; y++) { // search odds convertion chart
                    if (wkS2.equals(oddConvert[y][0])) {
                        wkS1 = oddConvert[y][1];
                        break;
                    }
                }// end for y
                if(wkS1 == "")
                    wkS1="9.8";
            }

        else{ //Mline odds > 9-1
            // get val of 1st 2 char
            wkS1=s.substring(0,2)+".";
            wkS2=s;

        }
        wkS = wkS2+"~"+wkS1+":";

       return wkS;
    } // end getOdds

    public static String getConditions(String s){
        int loc=0;
        String wkS="";

        if (s.contains("Thoroughbred")) {
            // get length of index# of char "-"
            loc = s.indexOf("-");

            switch(loc) {
                case 9:
                    // 9 claiming
                    wkS += "CLM";
                  //  loopIT = false;
                    break;
                case 10:
                    // allowance
                    wkS += "ALW";
                  //  loopIT = false;
                    break;
                case 16:
                    // MDN claiming
                    if (s.contains("MAIDEN")) {
                        wkS += "MCL";
                    }
                    else
                    { // waiver clm
                        wkS += "CLM";
                    }
                 //   loopIT = false;
                    break;
                case 18:
                    // 18 optional claiming
                    // Starter ALW
                    if (s.contains("STARTER")) {
                        wkS += "ALW";
                    } else
                        wkS += "CLW";

                  //  loopIT = false;
                    break;
                case 22:
                    // MDN Special wieght
                    wkS += "MSW";
                 //   loopIT = false;
                    break;
                case 23:
                    // waiver MDN clm
                    wkS += "MCL";
                  //  loopIT = false;
                    break;
                case 25:
                    // MDN optional clm
                    wkS += "MCL";
                 //   loopIT = false;
                    break;
                case 26:
                    // Starter optional clm
                    wkS += "CLM";
                    //   loopIT = false;
                    break;
                case 28:
                    // alw optional claiming
                    wkS += "AOC";
                 //   loopIT = false;
                    break;
                default:
                    // code block
                    if(s.contains("STAKES")) {
                        wkS += "STK";
                    }
                    else if(s.contains("HANDI")) {
                        wkS += "HND";
                    }
                    else
                        wkS += "UNK";
                    //wkS += "UNK";
                  //  loopIT = false;
            } // end switch

        } // end if s.contains "Thoro...
        else
            return "UKN";

        return wkS;
    } // end getConditions
    public static String getAgeSex(String s){
        String age="";
        String wkS="";
        if (s.toUpperCase().contains("THREE")){
            age =  "30";
            if (s.toUpperCase().contains("UPWARD"))
                age = "35";
            wkS += age;
        }
        else if (s.toUpperCase().contains("FOUR")){
            age =  "40";
            if (s.toUpperCase().contains("UPWARD"))
                age = "45";
            wkS += age;
        }
        else if (s.toUpperCase().contains("TWO")){
            age =  "20";
            if (s.toUpperCase().contains("UPWARD"))
                age = "25";
            wkS += age;
        }
        else
            wkS += "99"; // unknown age
        wkS += "_";
        // get sex not if does not contain fillie or Mare must be Male;
        if ( s.toUpperCase().contains("FILLIES") )
            wkS += "F_";
        else if (s.toUpperCase().contains("MARES" ) )
            wkS += "F_";
        else if (s.toUpperCase().contains("MAIDENS" ) )
            wkS += "F_";
        else
            wkS += "M_";

        return wkS;
    } // end get age
    public static String getDistanceSurface(String s){
        String dist ="";
        String surf = "";
        String wkS="";
        if (s.contains("Hurdle")) {
            dist = "99"; // unknown distanced
            wkS += dist;
        }
        else  if (s.contains("Miles")) {
            dist = "99"; // unknown distanced
            if (s.contains("Sixteenth"))
                dist = "86";
            if (s.contains("Eighth"))
                dist = "88";
            if (s.contains("Fourth"))
                dist = "84";
            // odd distance 1 3/16, 1 3/8, 1 1/4 and longer
            wkS += dist;
        }
        else if (s.contains("Five")){
            dist =  "50";
            if (s.contains("Half"))
                dist = "55";
            wkS += dist;
        }
        else if (s.contains("Six")){
            dist =  "60";
            if (s.contains("Half"))
                dist = "65";
            wkS += dist;
        }
        else if (s.contains("Seven")){
            dist =  "70";
            if (s.contains("Half"))
                dist = "75";
            wkS += dist;
        }
        else if (s.contains("Seven")){
            dist =  "70";
            if (s.contains("Half"))
                dist = "75";
            wkS += dist;
        }
        else if (s.contains("Mile")){
            dist =  "80";
            if (s.contains("Seventy"))
                dist = "87";
            wkS += dist;
        }
        else
            wkS += "99"; // unknown distance
        wkS += "_";
        // find surface
        if (s.contains("Dirt")){
            wkS += "D";
        }
        else if (s.contains("dirt")) {
            wkS += "D";
        }
        else if (s.contains("Turf")) {
            wkS += "T";
        }
        else if (s.contains("turf")) {
            wkS += "T";
        }
        else
            wkS += "S"; // synthedict

        return wkS;
    } // end getDistance
    public static String getSurfaceCondition(String s){
        // surface condition
        String surf = "";
        String wkS="";
        if (s.contains("Fast")){
            wkS += "F";
        }
        else if (s.contains("Firm")) {
            wkS += "F";
        }
        else if (s.contains("Good")) {
            wkS += "G";
        }
        else
            wkS += "O";

        wkS += "_";
        return wkS;
    }
    public static String getDaysWay(String s,String raceDate ){
        long noOfDaysBetween=0;
        Date d1= new Date();
        Date d2= new Date();
        DateFormat df1 = new SimpleDateFormat("yyyy/MM/dd");
        DateFormat df2 = new SimpleDateFormat("ddMMMyy");
        String wkS1="";
        String wkS2="";
        String returnLine="";

        try { // put string of race date in Date format

            d1 = df1.parse(raceDate);
            // System.out.println("Date in dd/MM/yyyy format is: " + df1.format(d1));

        } catch (Exception ex) {
            System.out.println(ex);
        }
        if(!s.substring(0,3).equals("---")) {
            wkS1 = s.substring(7, 8);
            if (wkS1.equals(" "))
                wkS2 = s.substring(0, 7);
            else {
                s = "0"+s;
                wkS2 = s.substring(0, 7);
            }


            try {

                d2 = df2.parse(wkS2);
                // System.out.println("Date in dd/MM/yyyy format is: " + df2.format(d2));

            } catch (Exception ex) {
                System.out.println(ex);

            } // end catch
            // get days away - convert Date to LocalDate
            //Getting the default zone id
            ZoneId defaultZoneId = ZoneId.systemDefault();

            //Converting the date to Instant
            Instant instant1 = d1.toInstant();
            //Converting the Date to LocalDate
            LocalDate ld1 = instant1.atZone(defaultZoneId).toLocalDate();
            //System.out.println("Local Date is: "+ld1);
            Instant instant2 = d2.toInstant();
            //Converting the Date to LocalDate
            LocalDate ld2 = instant2.atZone(defaultZoneId).toLocalDate();

            noOfDaysBetween = ChronoUnit.DAYS.between(ld2, ld1);

        }
        else
        { // 1st time starter
            noOfDaysBetween = 0; // no days
        }

        if (noOfDaysBetween == 0){
            //sb.append("  ----- 0 --- ");
            returnLine = "  ----- 0 --- "+s.substring(4);
            //  wkS1 = " "+String.valueOf(noOfDaysBetween);
            // sb.append(wkS1);
            //sb.append(s.substring(4));

        }

        else if (noOfDaysBetween < 100){
            wkS1 = String.valueOf(noOfDaysBetween);
            //sb.append(wkS2+" "+wkS1+s.substring(7));
            returnLine = wkS2+" "+wkS1+s.substring(7);
        }
        else { // greater than 100 call it 99
            noOfDaysBetween =99;
            wkS1 = String.valueOf(noOfDaysBetween);
            //sb.append(wkS2+" "+wkS1+s.substring(7));
            returnLine = wkS2+" "+wkS1+s.substring(7);

        }

        return returnLine;
    }




    public static void main(String[] args) {
	// Combine files to make a simplified and searchable file for data mining
        // get path from user
        byte cnt = 0;
        byte cnt1 = 0;
        int cntComb = 0;
        int typeML =0;
        ArrayList<String> listResults = new ArrayList<String>();
        ArrayList<String> listML = new ArrayList<String>();
        String track ="";
        String raceDate = "";
        String lastRaceDate = ""; // enteries last race date - note MDN race date will be current race date 0 zero days
        Date d1= new Date();
        Date d2= new Date();
        int dateSize=0;
        DateFormat df1 = new SimpleDateFormat("yyyy/MM/dd");
        DateFormat df2 = new SimpleDateFormat("ddMMMyy");
        String resultsFolderPathPrefix = "C:\\Users\\Kurt\\AllTracks\\";
        String resultsFolderPath = "";
        String wkS="";
        String[][] gradeTrack = new String[][]{{"AP", "C"}, {"BEL", "A"}, {"BTP", "C"}, {"CBY", "C"}, {"CT", "C"}, {"CD", "A"}, {"DMR", "A"},
                {"DEL", "B"}, {"ELP", "B"}, {"EVD", "C"}, {"FL", "C"}, {"GG", "C"}, {"GP", "B"}, {"IND", "B"}, {"LRL", "A"}, {"LS", "C"},
                {"LAD", "C"}, {"MTH", "B"}, {"PRX", "A"}, {"PEN", "C"}, {"RP", "B"}, {"SA", "A"}, {"SAR", "A"}, {"TDN", "C"}, {"WO", "A"},
                {"PIM", "C"},{"CNL", "C"},{"KD", "A"},{"TAM", "C"}};

        String[][] oddConvert = new String[][]{{"2/1", "2.0"}, {"3/1", "3.0"}, {"4/1", "4.0"}, {"5/1", "5.0"}, {"6/1", "6.0"}, {"7/1", "7.0"}, {"8/1", "8.0"},{"9/1","9.0"},
                {"1/1", "1.0"}, {"2/5", ".40"}, {"1/2", ".50"}, {"3/5", ".60"}, {"4/5", ".80"}, {"6/5", "1.2"}, {"7/5", "1.4"}, {"8/5", "1.6"}, {"9/5", "1.8"},
                {"3/2", "1.5"}, {"5/2", "2.5"}, {"7/2", "3.5"}, {"9/2", "4.5"} };

        String[][] wps = new String[][]{ {"","","",""}, {"","0.00","0.00","0.00"},
            {"","0.00","0.00","0.00"},{"","0.00","0.00","0.00"} };
        int wpsP=0;
        String wpsS=""; // a work var




        int gradeTrackLen = gradeTrack.length;

//        try {
            System.out.println("Enter the path (MonthYear June2019) to the folder to search for files");
            Scanner s1 = new Scanner(System.in);
            String folderPath = s1.next();
            File folder = new File(folderPath);
            if (folder.isDirectory()) {
                // listofFiles is an array of files
                File[] listOfFolders = folder.listFiles();
                if (listOfFolders.length < 1) System.out.println(
                        "There are no sub DayFolders inside User provided Parent (MonthYear) Folder");
                else System.out.println("List of Files & Folder");
                for (File file : listOfFolders) {// loops thru all folder names of days in month
                    //if(!file.isDirectory())System.out.println(
                    // file.getCanonicalPath().toString());
                    //folderPath = String.valueOf(listOfFiles[0]);
                    folderPath = String.valueOf(file);
                    System.out.println(folderPath);


                    File Subfolder = new File(folderPath); // all tracks in folder of day of the Month
                    cnt++;
                    if (Subfolder.isDirectory()) {
                        // listofFiles is an array of files
                        File[] SublistOfFiles = Subfolder.listFiles();
                        if (SublistOfFiles.length < 1) System.out.println(
                                "There are no track files inside Day of Month Folder");
                        else System.out.println("List of Files & Folder");

                        //    for (File subF : SublistOfFiles) {// loops thru all dayOfmonth track names
                        for (int sof = 0; sof < SublistOfFiles.length; sof++) { //
                            // only text files will be converted Results files
                            // ckecks all file names starting at first to last
                            // looks for ML file first , MLine.txt, then result file
                            // if no ML file converts result file only

                           // if (SublistOfFiles[i].getName().contains(".MLtxt")) {// folderPath is a string =Path + file name.pdf
                                if (SublistOfFiles[sof].getName().contains(".MLtxt")) {
                                // get ML arraylist
                                folderPath = String.valueOf(SublistOfFiles[sof]);

                                listML = getList(SublistOfFiles[sof]);
                                typeML =1;
                            } else if (SublistOfFiles[sof].getName().contains("MLine.txt")) {
                                // get ML arraylist
                                folderPath = String.valueOf(SublistOfFiles[sof]);

                                listML = getList(SublistOfFiles[sof]);
                                typeML = 2;
                                } else if (SublistOfFiles[sof].getName().contains("Mline.txt")) {
                                    // get ML arraylist
                                    folderPath = String.valueOf(SublistOfFiles[sof]);

                                    listML = getList(SublistOfFiles[sof]);
                                    typeML = 2;
                            } else if (SublistOfFiles[sof].getName().contains(".txt")) {
                                // get Results arraylist
                                folderPath = String.valueOf(SublistOfFiles[sof]);
                                String resFileName = String.valueOf(SublistOfFiles[sof].getName());
                                int wk = resFileName.length();
                                if (wk == 15) {
                                    // 2 char name
                                    track = resFileName.substring(0, 2);
                                    raceDate = "20"; // note this will have to change when doing year 2020
                                    raceDate += resFileName.substring(6, 8) + "/";
                                    raceDate += resFileName.substring(2, 4) + "/";
                                    raceDate += resFileName.substring(4, 6);
                                    dateSize = 8;
                                } else { // 3 char name
                                    // 3 char name
                                    track = resFileName.substring(0, 3);
                                    raceDate = "20"; // note this will have to change when doing year 2020
                                    raceDate += resFileName.substring(7, 9) + "/";
                                    raceDate += resFileName.substring(3, 5) + "/";
                                    raceDate += resFileName.substring(5, 7);
                                    dateSize = 9;
                                } //end wk == 15 - get track name and date of race

                                listResults = getList(SublistOfFiles[sof]);


// .MLtxt @ 509
// MLine.txt @ 1509
// Results @ 1685


// 08/29 Works to here opens ML & Result file and puts in to ARRAYLISTS
// now you need to build the combined file - test if ML is empty use different approach  when only having result file
// you may want to skip over results only files and resovle later ?????
                                // test if ML exists - true combine files /else convert just result file
                                ArrayList<String> listCombined = new ArrayList<String>();
                                ArrayList<String> listMLwithOdds = new ArrayList<String>();
                                ArrayList<String> listResLine = new ArrayList<String>();
                                StringBuilder[] sbwkML=new StringBuilder[20];
                                for(int sbML=0; sbML < sbwkML.length; sbML++) {
                                    sbwkML[sbML]=new StringBuilder();
                                }

                                int listMLsize = listML.size();
                                int listRsize = listResults.size();
                                int lp = 1; // loop thru listML array
                                int lpR = 1; // int val for race 1 of results
                                int loc =0; // loc = val of a index of a char location
                                int wk1=0; // work var
                                    int wk2 = 0;
                                    int wk3 = 0;
                                    int wkd1=0; // index val of '.'
                                    int wkd2=0;
                                    int wkd3=0;
                                int entryML=0; // cnt for each ML entry
                                String wkML = "";
                                String wkResConditions="";
                             //   resultsFolderPathPrefix = "C:\\Users\\Kurt\\AllTracks\\";
//////////////////////////////////////////////////////////////////////////////////////////////////////////
                                resultsFolderPathPrefix = "C:\\Users\\Kurt\\AllTR_debug\\";
//////////////////////////////////////////////////////////////////////////////////////////////////////////
                                    resultsFolderPathPrefix += track;
                                    File newPath = new File(resultsFolderPathPrefix);

                                    // true if the new.Path of directory was created, false otherwise
                                    if (newPath.mkdirs()) {
                                        System.out.println("Directory is created!");
                                    }

                                     resultsFolderPathPrefix += "\\" + resFileName.substring(0, dateSize) + "R";
///////////////////////////////////////////     .MLtxt  is true ////////////////////////////////////////////////////////////
                                if (typeML == 1) {
                                    // combine files use while loop
                                    typeML =0; // 0 is no Morning Line 1= .MLtxt 2= Mline.txt
// 894 where ML has actual odds attached
                                    while (lp <= listMLsize) {


                                        for (int numRace = 1; numRace <= 16; numRace++) {
                                            // put in track and date
                                            // test for no more races
                                            // for debug only
                                            lp++; // ML list cnt
                                            entryML=0;

                                            listCombined.clear();
                                            listCombined.add(raceDate);
                                            listCombined.add(track);

                                            // update file name
                                            resultsFolderPath = resultsFolderPathPrefix+String.valueOf(numRace) + "_";
                                            //get track grade
                                            for (int g = 0; g < gradeTrackLen; g++) {
                                                if (track.equals(gradeTrack[g][0])) { // comp string vals of track name
                                                    wkS = gradeTrack[g][1]+"_"; // wkS is a work String var
                                                    //resultsFolderPath +=
                                                    //g = gradeTrackLen;
                                                    break; // out of for loop
                                                }
                                            } // end for g : get track grade

                                            // get race type from listResults
                                            String s = "";
                                            String[] Mline = {"","","","","","","","",""};
                                            String wkS1 =""; // work string
                                            String wkS2="";
                                            boolean loopIT = true;
                                            long noOfDaysBetween=0;

                                            listResLine.clear(); // clear after race # loop
                                            listMLwithOdds.clear();

                                                s = listResults.get(lpR++);
                                            if(s.contains("Cancelled")) {
                                                numRace = 20;
                                                //break;
                                                lp = listMLsize+1;
                                                break;
                                            }

                                                // search contents of current line
                                                wkS += getConditions(s);

                                                // get sex and age
                                                wkS += "_";
                                                String age = "";
                                                s = listResults.get(lpR++);
                                            wkS += getAgeSex(s);




                                            // loop Result until you get to line with Record:
                                            while(!s.contains("Record:")){
                                                s = listResults.get(lpR++);
                                            }
                                            // line contailes distance
                                                wkS += getDistanceSurface(s);
                                            while(!s.contains("Weather:")){ // loop until line with weather
                                                s = listResults.get(lpR++);
                                            }
                                            wkS += getSurfaceCondition(s);

                                            // get winners and put in wps string
                                            wpsP = lpR;
                                            wpsS = s;
                                            while(!wpsS.contains("ace Sho")){ // loop until line with weather
                                                wpsS = listResults.get(wpsP++);
                                            } // loop until w p s header
                                            wps[1][0]=""; wps[1][1]="0.00"; wps[1][2]="0.00"; wps[1][3]="0.00";
                                            wps[2][0]=""; wps[2][1]="0.00"; wps[2][2]="0.00"; wps[2][3]="0.00";
                                            wps[3][0]=""; wps[3][1]="0.00"; wps[3][2]="0.00"; wps[3][3]="0.00";

                                            //Note need to test for no show bets and
                                            // winner
                                            wpsS = listResults.get(wpsP++);
                                            wpsS = getNameWin(wpsS);
                                            wk1=wpsS.indexOf(',');
                                            wk2=wpsS.indexOf(',',wk1+1);
                                            wps[1][0]=wpsS.substring(++wk1,wk2); // name
                                            wk3=wpsS.indexOf(',',++wk2);
                                            wps[1][1]=wpsS.substring(wk2,wk3); // win price
                                            boolean winShow = true;
                                            if(wpsS.indexOf(',',wk3+1) > 0) {
                                                // this means both place and show payoffs
                                                wk1 = wpsS.indexOf(',',++wk3);
                                                wps[1][2]=wpsS.substring(wk3,wk1); // place
                                                wps[1][3]=wpsS.substring(++wk1); // show


                                            }
                                            else { // only place price
                                               // wps[1][2]=wpsS.substring(wk3,wk1);
                                                wps[1][2]=wpsS.substring(++wk3); // place
                                                winShow = false;
                                            }

// 2nd horse
                                            wpsS = listResults.get(wpsP++);
                                            wpsS = getNameWin(wpsS);
                                            wk1=wpsS.indexOf(',');
                                            wk2=wpsS.indexOf(',',wk1+1);
                                            wps[2][0]=wpsS.substring(++wk1,wk2); // name
                                            // is it dead-heat
                                            wkd1 = wpsS.indexOf('.');
                                            wkd2 = wpsS.indexOf('.',wkd1+1);
                                            if(wkd2 > 0)
                                                wkd3 = wpsS.indexOf('.',wkd2+1);
                                            else
                                                wkd3 = -1;
                                            if(wkd3 > 0){
                                                // win deadheat
                                                // same code as winner
                                                wk3=wpsS.indexOf(',',++wk2);
                                                wps[2][1]=wpsS.substring(wk2,wk3); // win price

                                                if(wpsS.indexOf(',',wk3+1) > 0) {
                                                    // this means both place and show payoffs
                                                    wk1 = wpsS.indexOf(',',++wk3);
                                                    wps[2][2]=wpsS.substring(wk3,wk1); // place
                                                    wps[2][3]=wpsS.substring(++wk1); // show
                                                }


                                            } // end if wkd3 > 0
                                            else if(winShow){
                                                // 2nd place winner has place and show price
                                                // get place price
                                                wk3=wpsS.indexOf(',',++wk2);
                                                wps[2][2]=wpsS.substring(wk2,wk3); // place price
                                              //  wk1 = wpsS.indexOf(',',++wk3);
                                                wps[2][3]=wpsS.substring(++wk3); // show

                                            }
                                            else{
                                                // place only payoff
                                               // wk3=wpsS.indexOf(',',++wk2);
                                                wps[2][2]=wpsS.substring(++wk2); // place price
                                            }
// 3rd horse
                                            wpsS = listResults.get(wpsP++);
                                            wpsS = getNameWin(wpsS);
                                            wk1=wpsS.indexOf(',');
                                            wk2=wpsS.indexOf(',',wk1+1);
                                            if (wk2 >0)//has a show price
                                                wps[3][0]=wpsS.substring(++wk1,wk2);// name
                                            else
                                                wps[3][0]=wpsS.substring(++wk1);// name
                                            // is it dead-heat
                                            wkd1 = wpsS.indexOf('.');
                                            wkd2 = wpsS.indexOf('.',wkd1+1);
                                            if(wkd2 > 0)
                                            wkd3 = wpsS.indexOf('.',wkd2+1);
                                            else
                                               wkd3 = -1;
                                            if(wkd3 > 0){
                                                // win deadheat
                                                // same code as winner
                                                wk3=wpsS.indexOf(',',++wk2);
                                                wps[3][1]=wpsS.substring(wk2,wk3); // win price

                                                if(wpsS.indexOf(',',wk3+1) > 0) {
                                                    // this means both place and show payoffs
                                                    wk1 = wpsS.indexOf(',',++wk3);
                                                    wps[3][2]=wpsS.substring(wk3,wk1); // place
                                                    wps[3][3]=wpsS.substring(++wk1); // show
                                                }



                                            }
                                            else if(wkd2 > 0){
                                                // place deadheat
                                                wk3=wpsS.indexOf(',',++wk2);
                                                wps[3][2]=wpsS.substring(wk2,wk3); // place price
                                                //  wk1 = wpsS.indexOf(',',++wk3);
                                                wps[3][3]=wpsS.substring(++wk3); // show

                                            }
                                            else if(winShow){
                                                // Show price only
                                                wps[3][3]=wpsS.substring(++wk2); // show

                                            }
                                            // note if no winShow than there was no show betting in this race





                                            // search MLlist for # of entries
                                            s = listML.get(lp++);

                                            while(!s.contains("Selections:")){ // loop until ML line with Selections:
                                                s = listML.get(lp++);
                                            }
                                            loc = s.lastIndexOf(",");
                                            wk1 = s.length()- loc;

                                            //if (wk1 == 2) // field size < 10
                                                wkS += s.substring(++loc,s.length());
                                            loopIT = false;



                                            // at this point wkS should equal what race type and conditions
                                            resultsFolderPath += wkS;
                                            // add race type and conditions to combined list
                                            listCombined.add(wkS);



                                            // lp listMl index pointer should be pointed at 1st horse
                                            s = listML.get(lp++); // get first horse
                                            while(!s.contains("Owners:<")){ // loop until line with Owners

                                                // search thru s and find Mline convert it to odds

                                                for (int j=3; j < s.length(); j++){
                                                    wkML = s.substring(j,(j+1));
                                                    if (wkML.equals(",") ){
                                                        for (int x=(j+1); x < s.length(); x++){
                                                            wkML = s.substring(x,(x+1));
                                                            if (wkML.equals(",")){
                                                            wkML = s.substring(0,x);
                                                            j=s.length();
                                                            wk=wkML.length();
                                                            break;
                                                            }// end if x substring

                                                        }// end for x

                                                    } // end if wkML substring
                                                }// end for j
                                                // get string of odds starting from end moving backwards one char at a time find space - pull out odds
                                                wkS1="";
                                                wkS2="";
                                                for (int y=wk; y > 0; y--){
                                                    wkS1 = wkML.substring((y-1),y);
                                                    if (wkS1.equals(",")){
                                                        wkS2 = wkML.substring(y,(wk));
                                                        wk=wkS2.length();
                                                        break;
                                                    }// end if x substring
                                                }
                                                // if wk > 3 than odds are > 9-1
                                                wkS1="";
                                                if (wk == 3) {
                                                    for (int y = 0; y < 21; y++) { // search odds convertion chart
                                                        if (wkS2.equals(oddConvert[y][0])) {
                                                            wkS1 = oddConvert[y][1];
                                                            break;
                                                        }
                                                    }// end for y
                                                    if(wkS1 == "")
                                                        wkS1="9.8";
                                                }
                                                else
                                                { //Mline odds > 9-1
                                                    // get val of 1st 2 char
                                                    wkS1=wkS2.substring(0,2);
                                                    wkS1 += ".";

                                                }
                                                wkML +="~"+wkS1+":";
                                                //wkML += wkS1;
                                                // for debug only
                                                //if(numRace==8){
                                               //     cnt++;
                                                //}

                                                listMLwithOdds.add(wkML);
                                                  sbwkML[entryML++].append(wkML);
                                                s = listML.get(lp++); // get next horse

                                            } // end while loop s.listML not owners:


                                          /*  try { // put string of race date in Date format

                                                d1 = df1.parse(raceDate);
                                               // System.out.println("Date in dd/MM/yyyy format is: " + df1.format(d1));

                                            } catch (Exception ex) {
                                                System.out.println(ex);
                                            }
*/


//                                           Get odds from result charts Note scratches can effect horse # - search by name
                                            s = listResults.get(lpR++);
                                            while(!s.contains("Pgm")){ // loop until line with
                                                s = listResults.get(lpR++);

                                            } // loop thru junk info

                                            s = listResults.get(lpR++);
                                            // should be a first finisher of race

                                            while(!s.contains("Times:")) { // Result file looping to Split Times: line


                                                StringBuilder sb = new StringBuilder();


                                                // get date Note: 1st time  starts date r();
                                                sb.append(getDaysWay(s,raceDate));



                                                // get new line before looping
                                                listResLine.add(sb.toString());

                                                s = listResults.get(lpR++);
                                            }// end while not times
                                            int actualStarters = listResLine.size();
                                            for ( int E=0; E < actualStarters; E++)
                                                  { // loop thru starters get name and odds
// get actual odds
                                                      // get actual odds
                                                      s = listResLine.get(E);
                                                      loc = s.lastIndexOf('.');
                                                      wkS1 = s.substring((loc-3),(loc + 4));
                                                      String wkS3 = wkS1.substring(0,2);
                                                      char c1 = wkS3.charAt(0);
                                                      char c2 = wkS3.charAt(1);
                                                      if ( c1 != ' '){ // c1 is a number
                                                          if ( c2 == ' '){// c2 is a space so c1 is not a real # replace it with ' '
                                                              wkS3 = "  "+wkS1.substring(2);
                                                              wkS1 = wkS3;

                                                          }
                                                      }
                                                      // get name

                                                      loc = s.indexOf("(");
                                                      --loc;
                                                      int nameStart =0;
                                                      int wkName =0;
                                                      wkName = s.indexOf(" ",4);
                                                      //wkS2 = s.substring(wkName, loc);

                                                      wkName = s.indexOf(" ",++wkName);
                                                      //wkS2 = s.substring(wkName, loc);
                                                      wkName = s.indexOf(" ",++wkName);
                                                      nameStart = s.indexOf(" ",++wkName);




                                                      wkS2 = s.substring(++nameStart, loc);
                                                      /*if(wkS2.charAt(1) == ' ') {  // needed for when days away is < 10
                                                          wkS2 = wkS2.substring(2);
                                                      }
                                                      else if(wkS2.charAt(1) == ' ')
                                                          wkS2 = wkS2.substring(2);
*/
                                                      // update  sbwkMl
                                                      // search array for horse name
                                                      char dq =' ';
                                                      char dq1=' ';
                                                      char dq2=' ';
                                                      if(wkS2.length() > 2) {
                                                      dq = wkS2.charAt(0);
                                                      dq1 = wkS2.charAt(1);
                                                      dq2 = wkS2.charAt(2);

                                                          if (dq2 == '-')
                                                              if (dq1 == 'Q')
                                                                  if (dq == 'D')
                                                                      wkS2 = wkS2.substring(3);
                                                      }

                                                      int name = 0;
                                                      boolean testIT=false;
                                                      String nameFind=" ";
                                                      while(!sbwkML[name].equals("")){
                                                          nameFind = sbwkML[name].toString();
                                                           //int wkloc =   sbwkML[name].lastIndexOf(wkS2);
                                                          testIT = nameFind.contains(wkS2);

                                                            if(testIT){
                                                                sbwkML[name].append(wkS1);
                                                                break;
                                                                  //name++;

                                                                  } // end if
                                                        name++; // loop thru list
                                                      } // end while
                                            } // end for E actual starters



                                            // loop until wps pool
                                            s = listResults.get(lpR++);
                                            while(!s.contains("Pool")){ // loop Result file until find wps Pool
                                                s = listResults.get(lpR++);
                                                if(s.contains("Disqual")){
                                                    // cnt++;
                                                    listCombined.add(s);
                                                }
                                                if(s.contains("Scratched")){
                                                   // cnt++;
                                                    listCombined.add(s);
                                                }

                                            }
// listCombinded prior to Puting ML
                                            listCombined.add("Non-Sorted ML");

                                            while(!s.contains("Preview")){ // add result payoffs includes all exotics
                                                listResLine.add(s);
                                                s = listResults.get(lpR++);

                                            } // end while !Preview

                                            while(!s.contains("Copyright")){ // loop until line with weather
                                                s = listResults.get(lpR++);
                                            }
                                            lpR++;// +1 to move lpR pnt to line with "Thoroughbred"

                                            // sort Morling line fav and actual odds fav
                                            // name and ML converted odds into an array
                                            Float wkF =0.00f;
                                            Float[] oddsML = new Float[entryML];
                                            Float[] oddsMLrankedsort = new Float[entryML]; // this will hold ranked copy of oddsML[]
                                            Float[] oddsMLrsortNoscr = new Float[actualStarters];
                                            Float[] actualOdds = new Float[entryML];
                                            String[] nameMLrankedsort = new String[entryML];
                                            String[] nameMLrsortNoscr = new String[actualStarters];

                                            String[] nameSortMLfinal = new String[actualStarters];
                                            Float[] oddsSortMLfinal = new Float[actualStarters];

                                            String[] nameML = new String[entryML];
                                            String[] nameML2 = new String[entryML];
                                            String[] ml_oddsVar = new String[entryML];
                                            String[] ml_oddsVarNoscr = new String[actualStarters];

                                            String nameWK="";
                                            for(int E=0; E < entryML; E++){
                                                // get name
                                                nameWK = sbwkML[E].toString();
                                                int nameS = sbwkML[E].indexOf(",");
                                                int nameE = sbwkML[E].lastIndexOf(",");
                                                nameML[E] = sbwkML[E].substring(nameS+1,nameE).toString();
                                                int oddsS = sbwkML[E].indexOf("~");
                                                int oddsE = sbwkML[E].indexOf(":");
                                                nameWK = nameWK.substring(oddsS+1,oddsE);
                                                oddsML[E] = Float.parseFloat(nameWK);

                                            }// end for E
                                            // copy names in 2nd names array
                                            for(int E=0; E < entryML; E++){
                                                nameML2[E] = nameML[E];
                                            }

                                            // sort ML favorites
                                            int n = nameML.length;
                                            Float temp = 0.0f;
                                            String tempS="";
                                            for(int t=0; t < n; t++){
                                                for(int j=1; j < (n-t); j++){
                                                    if(oddsML[j-1] > oddsML[j]){
                                                        //swap elements
                                                        temp = oddsML[j-1];
                                                        tempS = nameML[j-1];
                                                        oddsML[j-1] = oddsML[j];
                                                        nameML[j-1] = nameML[j];
                                                        oddsML[j] = temp;
                                                        nameML[j] = tempS;
                                                    } // endif
                                                } // end for j
                                            } // end for i
                                            // append ML fav to sbwkML

                                            for(int E=0; E < entryML; E++){
                                                tempS = nameML[E];

                                                int t = 0;
                                                char scr = ' ';
                                                wkS="";
                                                while( t < entryML){
                                                    nameWK = sbwkML[t].toString();
                                                    if(nameWK.contains(tempS)){
                                                        wk = nameWK.length() -1;
                                                        scr = nameWK.charAt(wk);
                                                        if(scr == ':'){
                                                            wkS = "SCR";
                                                        }
                                                        sbwkML[t].append(" MLrank ");
                                                        sbwkML[t].append(E+1);
                                                        sbwkML[t].append("# "+wkS);
                                                        break; // end while loop
                                                    } // end if
                                                    t++;
                                                } // end while

                                            }  // end for E

                                            // now sort actual odds
                                            int noSCR =0;
                                            String nameInfo;
                                            boolean NotSCR = true;
                                            for(int E=0; E < entryML; E++){
                                                // get name
                                                nameWK = nameML[E];//sbwkML[E].toString();
                                                //int nameS = sbwkML[E].indexOf(",");
                                                //int nameE = sbwkML[E].lastIndexOf(",");

                                                Integer rank;
                                                Integer rankNoscr;

                                                // name&Odds are ranked ML

                                                nameMLrankedsort[E] = nameML[E];
                                                oddsMLrankedsort[E] = oddsML[E];
                                                rank = E+1;
                                                tempS = nameWK+"                    ";
                                                // find name in sbwkML if not scratch than add to ml_Odds... noSCR
                                                NotSCR = true;
                                                for(int i=0; i < entryML;i++){
                                                    nameInfo = sbwkML[i].toString();
                                                    if (nameInfo.contains(nameWK)){

                                                        if(nameInfo.contains("SCR")){
                                                            NotSCR = false;
                                                            break;
                                                        }
                                                        break;
                                                    }

                                                }

                                                if(NotSCR) {


                                                    // oddsML[E] = Float.parseFloat(nameWK);
                                                    oddsMLrsortNoscr[noSCR] = oddsML[E];
                                                    nameMLrsortNoscr[noSCR] = nameML[E];
                                                    oddsSortMLfinal[noSCR] = oddsML[E];
                                                    nameSortMLfinal[noSCR] = nameML[E];

                                                    rankNoscr = noSCR+1;
                                                    if(rankNoscr < 10) {
                                                        tempS = tempS.substring(0,20);
                                                    }
                                                    else {
                                                        tempS = tempS.substring(0, 19);
                                                    }
                                                    ml_oddsVarNoscr[noSCR++]=tempS + " ("+rankNoscr.toString()+")";  // ranked ML


                                                    // actualOdds[E] = oddsML[E];
                                                    //ml_oddsVar[E]+= "  "+oddsML[E].toString();
                                                }
                                                //else { // scratch
                                                //  oddsML[E] = 98.76f;
                                                //ml_oddsVar[E]+="  SRC";
                                                //}

                                            } // end for get odds
                                            // get acutal odds of non SCR horses
                                            for(int E=0; E < entryML; E++) {
                                                // get name

                                                nameWK = nameML[E]; // sbwkML[E].toString();
                                                for(int i=0; i < entryML;i++) {
                                                    nameInfo = sbwkML[i].toString();
                                                    if (nameInfo.contains(nameWK)) {
                                                        if (!nameInfo.contains("SCR")) {
                                                            int oddsS = sbwkML[i].indexOf(":");
                                                            int oddsE = sbwkML[i].indexOf(".", oddsS);
                                                            nameWK = sbwkML[i].substring(oddsS + 1, oddsE + 2);   // post position list
                                                            nameWK = nameWK.trim();
                                                           // oddsML[E] = Float.parseFloat(nameWK);
                                                            wkF = Float.parseFloat(nameWK);
                                                            wkF = getFloatOdds(wkF);
                                                            oddsML[E] = wkF;
                                                        } // end if !SCR
                                                        else {
                                                            oddsML[E]=987.65f;
                                                            break;
                                                        }
                                                        break;
                                                    } // end if nameWK
                                                } // for i loop
                                                nameInfo="";

                                            } // for loop E
// copy actual odds of MLsorted non-SCR
                                            noSCR=0;
                                            for(int i=0; i < entryML; i++){
                                                if (oddsML[i] < 900.00f){
                                                    actualOdds[noSCR++] = oddsML[i]; // actul odds sorded by non-SCR ML names
                                                }
                                            }

                                            // sort actual odds odds favorites
                                            n = nameML.length;
                                            temp = 0.0f;
                                            tempS="";
                                            for( int t=0; t < n; t++){
                                                for(int j=1; j < (n-t); j++){
                                                    if(oddsML[j-1] > oddsML[j]){
                                                        //swap elements
                                                        temp = oddsML[j-1];
                                                        tempS = nameML[j-1];
                                                        oddsML[j-1] = oddsML[j];
                                                        nameML[j-1] = nameML[j];
                                                        oddsML[j] = temp;
                                                        nameML[j] = tempS;
                                                    } // endif
                                                } // end for j
                                            } // end for i
                                            //
                                            // oddsML = actual odds sorted
                                            // nameML = name of actual odds sorted

                                            // copy contents
                                            for(int E=0; E < actualStarters; E++) {
                                                // copy name and odds name
                                                oddsMLrsortNoscr[E]=oddsML[E];
                                                nameMLrsortNoscr[E]=nameML[E];

                                            }


                                            // sort non-scr odds favorites
                                            n = nameMLrsortNoscr.length;
                                            temp = 0.0f;
                                            tempS="";
                                            for( int t=0; t < n; t++){
                                                for(int j=1; j < (n-t); j++){
                                                    if(oddsMLrsortNoscr[j-1] > oddsMLrsortNoscr[j]){
                                                        //swap elements
                                                        temp = oddsMLrsortNoscr[j-1];
                                                        tempS = nameMLrsortNoscr[j-1];
                                                        oddsMLrsortNoscr[j-1] = oddsMLrsortNoscr[j];
                                                        nameMLrsortNoscr[j-1] = nameMLrsortNoscr[j];
                                                        oddsMLrsortNoscr[j] = temp;
                                                        nameMLrsortNoscr[j] = tempS;
                                                    } // endif
                                                } // end for j
                                            } // end for i
                                            //
                                            // append ActualOdds fav to sbwkML

                                            noSCR=0;
                                            int t=0;
                                            for(int E=0;E < actualStarters;E++){
                                                tempS = nameMLrsortNoscr[E]; // names by actual odds sort

                                                t = 0;
                                                while( t < actualStarters){
                                                    //tempS = nameML[E];
                                                    if(ml_oddsVarNoscr[t].contains(tempS)){
                                                        Integer rank = E +1;
                                                        //sbwkML[t].append(" ODDSrank ");
                                                        //sbwkML[t].append(E+1);
                                                        ml_oddsVarNoscr[t] += " "+ rank.toString();
                                                        // actualOdds[t] = oddsMLrsortNoscr[E]; // odds by related namse  sort by ML no scr
                                                        break; // end while loop
                                                    } // end if

                                                    t++;
                                                } // end while


                                            }



/*
                                                nameML[E] = nameML2[E]; // copied name earlier
                                                int oddsS = sbwkML[E].indexOf(":");
                                                int oddsE = sbwkML[E].indexOf(".",oddsS);
                                                if(oddsE != -1) {
                                                    nameWK = nameWK.substring(oddsS + 1, oddsE + 2);
                                                    nameWK = nameWK.trim();
                                                    oddsML[E] = Float.parseFloat(nameWK);
                                                }
                                                else { // scratch
                                                    oddsML[E] = 98.76f;
                                                }
                                                } // end for
                                            // sort odds favorites
                                            n = nameML.length;
                                            temp = 0.0f;
                                            tempS="";
                                            for( int t=0; t < n; t++){
                                                for(int j=1; j < (n-t); j++){
                                                    if(oddsML[j-1] > oddsML[j]){
                                                        //swap elements
                                                        temp = oddsML[j-1];
                                                        tempS = nameML[j-1];
                                                        oddsML[j-1] = oddsML[j];
                                                        nameML[j-1] = nameML[j];
                                                        oddsML[j] = temp;
                                                        nameML[j] = tempS;
                                                    } // endif
                                                } // end for j
                                            } // end for i
*/
                                            //
                                            // append ActualOdds fav to sbwkML
                                            int postedWPS=0;

                                            for(int E=0; E < entryML; E++){
                                                tempS = nameML[E];
                                                Integer rank = E+1;


                                                t = 0;
                                                while( t < entryML){
                                                    nameWK = sbwkML[t].toString();
                                                    if(nameWK.contains(tempS)){
                                                        if(!nameWK.contains("SCR")) {
                                                            noSCR++;
                                                            sbwkML[t].append(" ODDSrank ");
                                                            sbwkML[t].append(noSCR);
                                                            // loop thru wps name to append payoffs
                                                            if (postedWPS < 3) { // if win ,place show are found than no need to check
                                                                //

                                                                // loop w,p,s
                                                                if (nameWK.contains(wps[1][0])) {

                                                                    sbwkML[t].append(" W! ");
                                                                    if (!wps[1][1].contains("0.00"))
                                                                        sbwkML[t].append(wps[1][1]);
                                                                    sbwkML[t].append(" P! ");
                                                                    if (!wps[1][2].contains("0.00"))
                                                                        sbwkML[t].append(wps[1][2]);
                                                                    sbwkML[t].append(" S! ");
                                                                    if (!wps[1][3].contains("0.00"))
                                                                        sbwkML[t].append(wps[1][3]);
                                                                    postedWPS++; // found winner
                                                                    //break;
                                                                 } // if wps[1][0]
                                                                else if(nameWK.contains(wps[2][0])) {

                                                                    sbwkML[t].append(" W! ");
                                                                    if (!wps[2][1].contains("0.00"))
                                                                        sbwkML[t].append(wps[2][1]);
                                                                    sbwkML[t].append(" P! ");
                                                                    if (!wps[2][2].contains("0.00"))
                                                                        sbwkML[t].append(wps[2][2]);
                                                                    sbwkML[t].append(" S! ");
                                                                    if (!wps[2][3].contains("0.00"))
                                                                        sbwkML[t].append(wps[2][3]);
                                                                    postedWPS++; // found place
                                                                    // break;
                                                                } // end if [2][0]
                                                                else if(nameWK.contains(wps[3][0])) {

                                                                    sbwkML[t].append(" W! ");
                                                                    if (!wps[3][1].contains("0.00"))
                                                                        sbwkML[t].append(wps[3][1]);
                                                                    sbwkML[t].append(" P! ");
                                                                    if (!wps[3][2].contains("0.00"))
                                                                        sbwkML[t].append(wps[3][2]);
                                                                    sbwkML[t].append(" S! ");
                                                                    if (!wps[3][3].contains("0.00"))
                                                                        sbwkML[t].append(wps[3][3]);
                                                                    postedWPS++; // found show
                                                                } // end if [3][0]

                                                            } // end if postedWPS
                                                        }// if !SCR



                                                            t = entryML +1; // end while loop
                                                        } // if name contains
                                                      //  break; // end while loop
                                                   // } // end if
                                                    t++;// = entryML +1;
                                                } // end while

                                            }  // end for E




                                            // add ML with actual odds to listCombind
                                            for(int E=0; E < entryML; E++){
                                                listCombined.add(sbwkML[E].toString());
                                            }
                                            postedWPS=0;
                                            for(int E=0; E < actualStarters; E++){
                                                //  listCombined.add(sbwkML[E].toString());
                                                // complete dbl ML strings
                                                // add ML odds Actual odds and dbl calculation
                                                // if(!ml_oddsVar[E].contains("SCR")) {
                                                ml_oddsVarNoscr[E] += " ML " + oddsSortMLfinal[E].toString();
                                                ml_oddsVarNoscr[E] += "  " + actualOdds[E].toString();
                                                Float tempF = (actualOdds[E]) / oddsSortMLfinal[E];
                                                tempS = tempF.toString()+"00";

                                                wk = tempS.indexOf('.');
                                                tempS = "  " + tempS.substring(0, (wk + 3));
                                                ;
                                                ml_oddsVarNoscr[E] += tempS;

                                                if (postedWPS < 3) { // if win ,place show are found than no need to check
                                                    //

                                                    // loop w,p,s
                                                    if (ml_oddsVarNoscr[E].contains(wps[1][0])) {

                                                        ml_oddsVarNoscr[E]+=" W! ";
                                                        if (!wps[1][1].contains("0.00"))
                                                            ml_oddsVarNoscr[E]+=wps[1][1];
                                                        ml_oddsVarNoscr[E]+=" P! ";
                                                        if (!wps[1][2].contains("0.00"))
                                                            ml_oddsVarNoscr[E]+=wps[1][2];
                                                        ml_oddsVarNoscr[E]+=" S! ";
                                                        if (!wps[1][3].contains("0.00"))
                                                            ml_oddsVarNoscr[E]+=wps[1][3];
                                                        postedWPS++; // found winner
                                                        //break;
                                                    } // if wps[1][0]
                                                    else if(ml_oddsVarNoscr[E].contains(wps[2][0])) {

                                                        ml_oddsVarNoscr[E]+=" W! ";
                                                        if (!wps[2][1].contains("0.00"))
                                                            ml_oddsVarNoscr[E]+=wps[2][1];
                                                        ml_oddsVarNoscr[E]+=" P! ";
                                                        if (!wps[2][2].contains("0.00"))
                                                            ml_oddsVarNoscr[E]+=wps[2][2];
                                                        ml_oddsVarNoscr[E]+=" S! ";
                                                        if (!wps[2][3].contains("0.00"))
                                                            ml_oddsVarNoscr[E]+=wps[2][3];
                                                        postedWPS++; // found place
                                                        // break;
                                                    } // end if [2][0]
                                                    else if(ml_oddsVarNoscr[E].contains(wps[3][0])) {

                                                        ml_oddsVarNoscr[E]+=" W! ";
                                                        if (!wps[3][1].contains("0.00"))
                                                            ml_oddsVarNoscr[E]+=wps[3][1];
                                                        ml_oddsVarNoscr[E]+=" P! ";
                                                        if (!wps[3][2].contains("0.00"))
                                                            ml_oddsVarNoscr[E]+=wps[3][2];
                                                        ml_oddsVarNoscr[E]+=" S! ";
                                                        if (!wps[3][3].contains("0.00"))
                                                            ml_oddsVarNoscr[E]+=wps[3][3];
                                                        postedWPS++; // found show
                                                    } // end if [3][0]

                                                } // end if postedWPS


                                                tempF = 0.00f;
                                                // } // end if
                                            } // end for


                                            wk--;
                                            // test if end of result file

                                           // int actualStarters = listResLine.size();
                                            //wkS += "99_"+String.valueOf(actualStarters);
                                                resultsFolderPath +="_" +String.valueOf(actualStarters)+".txt";
                                                System.out.println(resultsFolderPath);

                                                File cfout = new File(resultsFolderPath); // Converted file
                                                try { // the following saves the completed listCombined of the individual races
                                                    // listCombined is ArrayList of combined files
                                                    PrintWriter output = new PrintWriter(cfout);
                                                    for (int line = 0; line < listCombined.size(); line++) {
                                                        output.println(listCombined.get(line)); // this works 09/01/20
                                                    } // end for line
                                                    output.println("Sort by ML  (ML rank) Odds rank  ML Odds Ratio");

                                                    for (int line = 0; line < ml_oddsVarNoscr.length; line++) {
                                                        output.println(ml_oddsVarNoscr[line]); // this works 09/01/20
                                                    } // end for line

                                                    output.println("Finish Results");

                                                    for (int line = 0; line < listResLine.size(); line++) {
                                                        output.println(listResLine.get(line)); // this works 09/01/20
                                                    } // end for line
                                                    output.close();
                                                } catch (IOException e) {
                                                    e.printStackTrace();
                                                }
                                                // reset sbwk to all = ""
                                                //for (int sb =0; sb < 20; sb++){
                                                ///  sbwkML[sb].equals("");
                                                //}
                                                for (int sbML = 0; sbML < sbwkML.length; sbML++) {
                                                    sbwkML[sbML] = new StringBuilder();
                                                }
                                            if( lpR == listRsize){
                                                // no more races
                                                numRace = 20;
                                                lp = (listMLsize + 10);
                                                cnt++;
                                            }



                                            cnt1++; // debug only

                                                //folder = new File(folderPath);
                                             cnt1++;
                                            // debug break out of loop
                                           // break;
                                            cancelled:;
                                        } // end for numRace





                                    } // end while will loop thru all listML


                                } // end if ML file
                               // else{
///////////////////////////////////////////     MLine.txt  is true ////////////////////////////////////////////////////////////

                                 else if (typeML == 2) {
                                        // combine files use while loop
                                    typeML =0; // 0 is no Morning Line 1= .MLtxt 2= Mline.txt

                                    while (lp <= listMLsize) {

                                        String s = "";
                                        int maxRaces=0;
                                        int wkL=0;
                                        int wkL1=0;

                                        s = listML.get(lp++);
                                        // Jump

                                        while(!s.contains("Jump")){ // loop until ML line with Selections:
                                            s = listML.get(lp++);
                                        }
                                        s = s.trim();

                                        wkL= s.lastIndexOf('|');
                                        wkL1 = s.length();
                                        wkL = wkL1-wkL;
                                        if(wkL == 3)
                                            maxRaces = s.charAt(wkL1 -1);
                                        else{ // more than 9 races
                                            maxRaces = s.charAt(wkL1 -1);
                                            maxRaces += 10;
                                        }
                                        maxRaces -= 47;


                                            for (int numRace = 1; numRace < maxRaces; numRace++) {
                                                // put in track and date
                                                // test for no more races
                                                // for debug only

                                                lp++; // ML list cnt
                                                entryML=0;
                                                listCombined.clear();
                                                listCombined.add(raceDate);
                                                listCombined.add(track);


                                                // update file name
                                                resultsFolderPath = resultsFolderPathPrefix+String.valueOf(numRace) + "_";
                                                //get track grade
                                                for (int g = 0; g < gradeTrackLen; g++) {
                                                    if (track.equals(gradeTrack[g][0])) { // comp string vals of track name
                                                        wkS = gradeTrack[g][1]+"_"; // wkS is a work String var
                                                        //resultsFolderPath +=
                                                        //g = gradeTrackLen;
                                                        break; // out of for loop
                                                    }
                                                } // end for g : get track grade

                                                // get race type from listResults
                                                s="";
                                                String[] snif ={"","","","","","","","","",""};
                                                int snifCnt =0;


                                                String[] Mline = {"","","","","","","","",""};
                                                String wkS1 =""; // work string
                                                String wkS2="";
                                                boolean loopIT = true;
                                                long noOfDaysBetween=0;

                                                listResLine.clear(); // clear after race # loop
                                                listMLwithOdds.clear();

                                                s = listResults.get(lpR++);
                                                // check if races have been cancelled
                                                if(s.contains("Cancelled")) {
                                                    numRace = 20;
                                                    //break;
                                                    lp = listMLsize+1;
                                                    break;
                                                }

                                                // search contents of current line
                                                wkS += getConditions(s);
                                                // get sex and age
                                                wkS += "_";
                                                String age = "";
                                                s = listResults.get(lpR++);
                                                wkS += getAgeSex(s);




                                                // loop Result until you get to line with Record:
                                                while(!s.contains("Record:")){
                                                    s = listResults.get(lpR++);
                                                }
                                                // line containes distance
                                                wkS += getDistanceSurface(s);
                                                while(!s.contains("Weather:")){ // loop until line with weather
                                                    s = listResults.get(lpR++);
                                                }
                                                wkS += getSurfaceCondition(s);
                                                resultsFolderPath += wkS;
                                                wkResConditions = wkS;

                                                // get winners and put in wps string
                                                wpsP = lpR;
                                                wpsS = s;
                                                while(!wpsS.contains("ace Sho")){ // loop until line with weather
                                                    wpsS = listResults.get(wpsP++);
                                                } // loop until w p s header
                                                wps[1][0]=""; wps[1][1]="0.00"; wps[1][2]="0.00"; wps[1][3]="0.00";
                                                wps[2][0]=""; wps[2][1]="0.00"; wps[2][2]="0.00"; wps[2][3]="0.00";
                                                wps[3][0]=""; wps[3][1]="0.00"; wps[3][2]="0.00"; wps[3][3]="0.00";

                                                //Note need to test for no show bets and
                                                // winner
                                                wpsS = listResults.get(wpsP++);
                                                wpsS = getNameWin(wpsS);
                                                wk1=wpsS.indexOf(',');
                                                wk2=wpsS.indexOf(',',wk1+1);
                                                wps[1][0]=wpsS.substring(++wk1,wk2); // name
                                                wk3=wpsS.indexOf(',',++wk2);
                                                wps[1][1]=wpsS.substring(wk2,wk3); // win price
                                                boolean winShow = true;
                                                if(wpsS.indexOf(',',wk3+1) > 0) {
                                                    // this means both place and show payoffs
                                                    wk1 = wpsS.indexOf(',',++wk3);
                                                    wps[1][2]=wpsS.substring(wk3,wk1); // place
                                                    wps[1][3]=wpsS.substring(++wk1); // show


                                                }
                                                else { // only place price
                                                    // wps[1][2]=wpsS.substring(wk3,wk1);
                                                    wps[1][2]=wpsS.substring(++wk3); // place
                                                    winShow = false;
                                                }

// 2nd horse
                                                wpsS = listResults.get(wpsP++);
                                                wpsS = getNameWin(wpsS);
                                                wk1=wpsS.indexOf(',');
                                                wk2=wpsS.indexOf(',',wk1+1);
                                                wps[2][0]=wpsS.substring(++wk1,wk2); // name
                                                // is it dead-heat
                                                wkd1 = wpsS.indexOf('.');
                                                wkd2 = wpsS.indexOf('.',wkd1+1);
                                                if(wkd2 > 0)
                                                    wkd3 = wpsS.indexOf('.',wkd2+1);
                                                else
                                                    wkd3 = -1;
                                                if(wkd3 > 0){
                                                    // win deadheat
                                                    // same code as winner
                                                    wk3=wpsS.indexOf(',',++wk2);
                                                    wps[2][1]=wpsS.substring(wk2,wk3); // win price

                                                    if(wpsS.indexOf(',',wk3+1) > 0) {
                                                        // this means both place and show payoffs
                                                        wk1 = wpsS.indexOf(',',++wk3);
                                                        wps[2][2]=wpsS.substring(wk3,wk1); // place
                                                        wps[2][3]=wpsS.substring(++wk1); // show
                                                    }


                                                } // end if wkd3 > 0
                                                else if(winShow){
                                                    // 2nd place winner has place and show price
                                                    // get place price
                                                    wk3=wpsS.indexOf(',',++wk2);
                                                    wps[2][2]=wpsS.substring(wk2,wk3); // place price
                                                    //  wk1 = wpsS.indexOf(',',++wk3);
                                                    wps[2][3]=wpsS.substring(++wk3); // show

                                                }
                                                else{
                                                    // place only payoff
                                                    // wk3=wpsS.indexOf(',',++wk2);
                                                    wps[2][2]=wpsS.substring(++wk2); // place price
                                                }
// 3rd horse
                                                wpsS = listResults.get(wpsP++);
                                                wpsS = getNameWin(wpsS);
                                                wk1=wpsS.indexOf(',');
                                                wk2=wpsS.indexOf(',',wk1+1);
                                                if (wk2 >0)//has a show price
                                                    wps[3][0]=wpsS.substring(++wk1,wk2);// name
                                                else
                                                    wps[3][0]=wpsS.substring(++wk1);// name
                                                // is it dead-heat
                                                wkd1 = wpsS.indexOf('.');
                                                wkd2 = wpsS.indexOf('.',wkd1+1);
                                                if(wkd2 > 0)
                                                    wkd3 = wpsS.indexOf('.',wkd2+1);
                                                else
                                                    wkd3 = -1;
                                                if(wkd3 > 0){
                                                    // win deadheat
                                                    // same code as winner
                                                    wk3=wpsS.indexOf(',',++wk2);
                                                    wps[3][1]=wpsS.substring(wk2,wk3); // win price

                                                    if(wpsS.indexOf(',',wk3+1) > 0) {
                                                        // this means both place and show payoffs
                                                        wk1 = wpsS.indexOf(',',++wk3);
                                                        wps[3][2]=wpsS.substring(wk3,wk1); // place
                                                        wps[3][3]=wpsS.substring(++wk1); // show
                                                    }



                                                }
                                                else if(wkd2 > 0){
                                                    // place deadheat
                                                    wk3=wpsS.indexOf(',',++wk2);
                                                    wps[3][2]=wpsS.substring(wk2,wk3); // place price
                                                    //  wk1 = wpsS.indexOf(',',++wk3);
                                                    wps[3][3]=wpsS.substring(++wk3); // show

                                                }
                                                else if(winShow){
                                                    // Show price only
                                                    wps[3][3]=wpsS.substring(++wk2); // show

                                                }
                                                // note if no winShow than there was no show betting in this race





                                                // search MLlist for # of entries
                                                s = listML.get(lp++);
                                                // NOTE Mline txt does not give # of entries for each race

                                                while(!s.contains("M/L")){ // loop until ML line with Selections:
                                                    s = listML.get(lp++);
                                                }
                                                s = listML.get(lp++);
                                                //s.trim();
                                                //snif[0] = listML.get(lp);
                                                //snif[0].trim();



/*
                                                loc = s.IndexOf("("); // finds end of horse name
                                                wk1 = s.length()- loc;

                                                //if (wk1 == 2) // field size < 10
                                                wkS += s.substring(++loc,s.length());
                                                loopIT = false;



                                                // at this point wkS should equal what race type and conditions
                                                resultsFolderPath += wkS;
                                                // add race type and conditions to combined list
                                                listCombined.add(wkS);



                                                // lp listMl index pointer should be pointed at 1st horse
                                                s = listML.get(lp++); // get first horse
*/
                                                String[] nH ={"2 2","3 3","4 4","5 5","6 6","7 7","8 8","9 9","10 10",
                                                        "SCR",
                                                              "11 11","12 12","13 13","14 14","15 15","16 16","17 17"};
                                                while(!s.contains("Owners:")){ // loop until line with Owners

                                                    // search thru s and find Mline convert it to odds
                                                    // line may be broken up so loop until sniff = next horse
//                                                    for (int nextH=0; nextH < 20; nextH++){
//
//
//                                                    } // end for nextH
                                                    // Test length if < 5 then s= "1 1 "
                                                    // next line will contain name
                                                    wkS = listML.get((lp)); // look ahead 1
                                                    if (wkS.contains("Owners:") && s.length() == 1) {
                                                        s = listML.get(lp++); // skip next should be owners
                                                        break;
                                                    }

                                                    if(s.contains("Coupled")){
                                                        s = listML.get(lp++); // skip next should be owners
                                                        break;
                                                    }
                                                    if(s.contains("Reported")){
                                                        s = listML.get(lp++);// skip next should be owners
                                                        break;
                                                    }
                                                    if(s.contains("Live") && s.length() < 7){
                                                        // make sure its not horse name
                                                     //   wkS = s.substring(0,4);
                                                     //   if(s.substring(0,4) == "Live") {
                                                            s = listML.get(lp++);// = Odds
                                                            s = listML.get(lp++);// = 1st hourse
                                                       // }

                                                    }
                                                    if(s.contains("P#")){
                                                        s = listML.get(lp++);
                                                        while(!s.contains("M/L")){ // loop until ML line with Selections:
                                                            s = listML.get(lp++);
                                                        }
                                                        s = listML.get(lp++);
                                                    }
                                                    int sL = s.length();
                                                    if (sL==1) {
                                                        // empty line - get next line
                                                        s = listML.get(lp++); // should be horse PP
                                                        sL = s.length();
                                                    }
                                                    if( s.contains("(") && s.charAt(0) !=' ') {
                                                        // get entry # and horse name
                                                        // find first " " if =1 not any entry ex. 1A else entry 1A
                                                        int sLoc = s.indexOf(" ");
                                                        sL = s.length();
                                                        if (sL > 35){
                                                            // most likely complete line of horse info
                                                            // trim s
                                                            s = s.trim();
                                                            sL = s.length();sL = s.length();
                                                        }
                                                        char sLast = s.charAt(sL - 1);
                                                        if (sLast == ' ') sL--;

                                                        int sNend = s.indexOf("(") - 1; // pnt to last char of name
                                                        if (sLoc == 1) { // horse position info 1st 4 char
                                                            wkML = s.substring(0, 3) + ",";
                                                            sLoc = 4; // start of name 4th char
                                                        } else {
                                                            wkML = s.substring(0, 4) + ",";
                                                            sLoc = 5;
                                                        }
                                                        // get name sLoc = first char
                                                        wkS = wkML + s.substring(sLoc, sNend) + ",";


                                                        char wkC = s.charAt(sL - 2);

                                                        if (wkC == '/') { // s contains the odds
                                                            wkML = s.substring((sL - 4), sL);
                                                            wkS += getOdds(wkML);// wkS pp+name+odds
                                                        } else { // s. contains name but not odds
                                                            // loop snif to find odds
                                                            //int l4=4;
                                                            //for(int j=0; j < 10;j++){
                                                            // keep feeding s until odds are found
                                                            s = listML.get(lp++);
                                                            wkS1 = s.trim();
                                                            s = wkS1;
                                                            sL = s.length();
                                                            //sL = s.length();
                                                            if(sL < 2){ s += "^^"+s; sL=2;}
                                                            wkC = s.charAt(sL - 2);
                                                            int wkC1 = s.charAt(sL - 1);

                                                            if (wkC == '/' && wkC1 < 65) { // s contains the odds
                                                                wkML = s.substring((sL - 4), sL);
                                                                wkS += getOdds(wkML);// wkS pp+name+odds
                                                            } else { // does not contain odds
                                                                // loop snif to find odds
                                                                int l4 = 4;
                                                                for (int j = 0; j < 10; j++) {
                                                                    // keep feeding s until odds are found
                                                                    s = listML.get(lp++);
                                                                    wkS1 = s.trim();
                                                                    s = wkS1;
                                                                    sL = s.length();
                                                                    switch (sL) {
                                                                        case 2:
                                                                            s = "^" + s;
                                                                            sL = 3;
                                                                            break;
                                                                        case 1:
                                                                            s = "^^" + s;
                                                                            sL = 3;
                                                                            break;
                                                                        case 0:
                                                                            s = "^^^";
                                                                            sL = 3;
                                                                            break;
                                                                        default:
                                                                            break;
                                                                    }


                                                                    sLast = s.charAt(sL - 1);

                                                                    if (sL < 4) l4 = 3; // s  = odds < 10-1
                                                                    if (sLast == ' ') sL--;

                                                                    wkC = s.charAt(sL - 2);
                                                                    wkC1 = s.charAt(sL - 1);

                                                                    if (wkC == '/' && wkC1 < 65) { // s contains the odds ascii A = 65
                                                                        wkML = s.substring((sL - l4), sL);
                                                                        if (wkML.length() == 3) wkML = " " + wkML;
                                                                        wkS += getOdds(wkML); // wkS pp+name+odds
                                                                        break;
                                                                    }

                                                                } // end for j
                                                            } // end else s. does not contain odds

                                                        } // end if s.contains  (
                                                    }
                                                    else { // s.1 SCR or s. !cont "("
                                                        // must determine if entry or scratch or blank
                                                        // start with scratch
                                                        sL = s.length();
                                                        if (sL==1) {
                                                            // empty line - get next line
                                                            s = listML.get(lp++); // should be horse PP
                                                            sL = s.length();
                                                        }
                                                        if (s.contains("SCR")) { // entry is a scratch
                                                            wkML = s.substring(1, 4) + ","; // pp
                                                            // get name
                                                            if (s.contains("(")) {

                                                                int sNend = s.indexOf("(") - 1; // pnt to last char of name
                                                                int sLoc = 6; // name starting position

                                                                wkS = wkML + s.substring(sLoc, sNend) + ",";

                                                            }// end if s contains "("
                                                            else { // get name - not on first line only has " SCR  "
                                                                // could all be on next line or next 2 lines
                                                                s = listML.get(lp++);

                                                                if (!s.contains("(")) wkS1 = s;
                                                                s = listML.get(lp++);
                                                                if (s.contains("(")) {
                                                                    int sNend = s.indexOf("(") - 1; // pnt to last char of name
                                                                    int sLoc = 0; // name starting position
                                                                    if (sNend > 0)
                                                                        wkS = wkML + wkS1 + s.substring(sLoc, sNend) + ",";
                                                                    else {
                                                                        wkS1 = wkS1.trim();
                                                                        wkS = wkML + wkS1 + ",";
                                                                    }

                                                                } // end if s.cont "(" & SCR
                                                                else {
                                                                    // error
                                                                    wkML = s; // this means SCR coding broke
                                                                }

                                                            }// end else s !contains "(" only has SCR

                                                            sL = s.length();
                                                            char wkC = s.charAt(sL - 2);

                                                            if (wkC == '/') { // s contains the odds
                                                                wkML = s.substring((sL - 4), sL);
                                                                wkS += getOdds(wkML);// wkS pp+name+odds
                                                            } else { //
                                                                // loop snif to find odds
                                                                int l4 = 4;
                                                                for (int j = 0; j < 10; j++) {
                                                                    // keep feeding s until odds are found
                                                                    l4=4;

                                                                    s = listML.get(lp++);
                                                                    wkS1 = s.trim();
                                                                    s = wkS1;

                                                                    sL = s.length();
                                                                    char sLast = s.charAt(sL - 1);
                                                                    if (sL < 4) l4 = 3; // s  = odds < 10-1
                                                                    if (sLast == ' ') sL--;
                                                                    wkC = s.charAt(sL - 2);

                                                                    if (wkC == '/') { // s contains the odds
                                                                        wkML = s.substring((sL - l4), sL);
                                                                        if (wkML.length() == 3) wkML = " " + wkML;
                                                                        wkS += getOdds(wkML); // wkS pp+name+odds
                                                                        break;
                                                                    }
                                                                } // end for j
                                                            } // end else s. does not contain odds


                                                        } // end if SRC
                                                        else { // s Not Scratch and s not contains name
                                                            // 1st line at minimum should have pp could have part of name
                                                            // get lenth of s if grater than 5 contains part of name
                                                            sL = s.length();
                                                            // could be char(32)
                                                            if (sL==1) {
                                                                // empty line - get next line
                                                                s = listML.get(lp++); // should be horse PP
                                                                sL = s.length();
                                                            }

                                                            int sLoc = s.indexOf(" ");
                                                            if(sLoc == 1){ // horse position info 1st 4 char
                                                                wkML = s.substring(0,3)+",";
                                                                sLoc = 4; // start of name 4th char
                                                                wkML += s.substring(sLoc);
                                                            }
                                                            else {
                                                                wkML = s.substring(0, 4) + ",";
                                                                sLoc = 5;
                                                                wkML += s.substring(sLoc);
                                                            }

                                                            s = listML.get(lp++);

                                                            if (!s.contains("(")){ wkS1 = s;
                                                            s = listML.get(lp++);}

                                                            if (s.contains("(")) {
                                                                int sNend = s.indexOf("(") - 1; // pnt to last char of name
                                                                sLoc = 0; // name starting position
                                                                if (sNend > 0)
                                                                    wkS = wkML + wkS1 + s.substring(sLoc, sNend) + ",";
                                                                else {
                                                                    wkS1 = wkS1.trim();
                                                                    wkS = wkML + wkS1 + ",";
                                                                }
                                                            }
                                                            sL = s.length();
                                                            char wkC = s.charAt(sL - 2);
                                                            int wkC1 = s.charAt(sL-1);

                                                            if (wkC == '/' && wkC1 < 65) { // s contains the odds
                                                                wkML = s.substring((sL - 4), sL);
                                                                wkS += getOdds(wkML);// wkS pp+name+odds
                                                            } else { // does not contain odds
                                                                // loop snif to find odds
                                                                int l4 = 4;
                                                                for (int j = 0; j < 10; j++) {
                                                                    l4=4;
                                                                    // keep feeding s until odds are found
                                                                    s = listML.get(lp++);
                                                                    wkS1 = s.trim();
                                                                    s = wkS1;
                                                                    sL = s.length();
                                                                    switch (sL){
                                                                        case 2:
                                                                            s="^"+s;
                                                                            sL=3;
                                                                            break;
                                                                        case 1:
                                                                            s="^^"+s;
                                                                            sL=3;
                                                                            break;
                                                                        case 0:
                                                                            s="^^^";
                                                                            sL=3;
                                                                            break;
                                                                        default:
                                                                            break;
                                                                    }


                                                                    char sLast = s.charAt(sL - 1);

                                                                    if (sL < 4) l4 = 3; // s  = odds < 10-1
                                                                    if (sLast == ' ') sL--;

                                                                    wkC = s.charAt(sL - 2);
                                                                    wkC1 = s.charAt(sL-1);

                                                                    if (wkC == '/' && wkC1 < 65) { // s contains the odds ascii A = 65
                                                                        wkML = s.substring((sL - l4), sL);
                                                                        if (wkML.length() == 3) wkML = " " + wkML;
                                                                        wkS += getOdds(wkML); // wkS pp+name+odds
                                                                        break;
                                                                    }
                                                                } // end for j
                                                            } // end else s. does not contain odds


                                                        //} // end if SRC


                                                            }// end else Not SCR and s. doesnt have complete name



                                                        } // end else s.1



                                                    listMLwithOdds.add(wkS);
                                                    sbwkML[entryML++].append(wkS);
                                                    s = listML.get(lp++); // get next horse

                                                } // end while loop s.listML not owners:


                                          /*  try { // put string of race date in Date format

                                                d1 = df1.parse(raceDate);
                                               // System.out.println("Date in dd/MM/yyyy format is: " + df1.format(d1));

                                            } catch (Exception ex) {
                                                System.out.println(ex);
                                            }
*/


//                                           Get odds from result charts Note scratches can effect horse # - search by name
                                                resultsFolderPath += String.valueOf(entryML)+"_";
                                                wkResConditions += String.valueOf(entryML);
                                                listCombined.add(wkResConditions);

                                                s = listResults.get(lpR++);
                                                while(!s.contains("Pgm")){ // loop until line with
                                                    s = listResults.get(lpR++);

                                                } // loop thru junk info

                                                s = listResults.get(lpR++);
                                                // should be a first finisher of race

                                                while(!s.contains("Times:")) { // Result file looping to Split Times: line


                                                    StringBuilder sb = new StringBuilder();


                                                    // get date Note: 1st time  starts date r();
                                                    sb.append(getDaysWay(s,raceDate));

                                                /*if(!s.substring(0,3).equals("---")) {
                                                    wkS1 = s.substring(7, 8);
                                                    if (wkS1.equals(" "))
                                                        wkS2 = s.substring(0, 7);
                                                    else {
                                                        s = "0"+s;
                                                        wkS2 = s.substring(0, 7);
                                                    }


                                                    try {

                                                        d2 = df2.parse(wkS2);
                                                       // System.out.println("Date in dd/MM/yyyy format is: " + df2.format(d2));

                                                    } catch (Exception ex) {
                                                        System.out.println(ex);

                                                    } // end catch
                                                    // get days away - convert Date to LocalDate
                                                    //Getting the default zone id
                                                    ZoneId defaultZoneId = ZoneId.systemDefault();

                                                    //Converting the date to Instant
                                                    Instant instant1 = d1.toInstant();
                                                    //Converting the Date to LocalDate
                                                    LocalDate ld1 = instant1.atZone(defaultZoneId).toLocalDate();
                                                    //System.out.println("Local Date is: "+ld1);
                                                    Instant instant2 = d2.toInstant();
                                                    //Converting the Date to LocalDate
                                                    LocalDate ld2 = instant2.atZone(defaultZoneId).toLocalDate();

                                                    noOfDaysBetween = ChronoUnit.DAYS.between(ld2, ld1);

                                                }
                                                else
                                                { // 1st time starter
                                                    noOfDaysBetween = 0; // no days
                                                }

                                                if (noOfDaysBetween == 0){
                                                    sb.append("  ----- 0 --- ");
                                                  //  wkS1 = " "+String.valueOf(noOfDaysBetween);
                                                   // sb.append(wkS1);
                                                    sb.append(s.substring(4));

                                                }
                                                *//*else if(noOfDaysBetween < 10){
                                                    // format days way to be 2 chars
                                                    wkS1 = " "+String.valueOf(noOfDaysBetween);
                                                    sb.append(wkS2+" "+wkS1+s.substring(7));
                                                 //   s.add(0," ");
                                                }*//*
                                                else if (noOfDaysBetween < 100){
                                                    wkS1 = String.valueOf(noOfDaysBetween);
                                                    sb.append(wkS2+" "+wkS1+s.substring(7));
                                                }
                                                else { // greater than 100 call it 99
                                                    noOfDaysBetween =99;
                                                    wkS1 = String.valueOf(noOfDaysBetween);
                                                    sb.append(wkS2+" "+wkS1+s.substring(7));
                                                }

                                             //   wkS1 = convertStringToDateML(raceDate);

                                             //   wkS1 = s.substring(0, (3));
*/


                                                    // get new line before looping
                                                    listResLine.add(sb.toString());

                                                    s = listResults.get(lpR++);
                                                }// end while not times
                                                int actualStarters = listResLine.size();
                                                for ( int E=0; E < actualStarters; E++)
                                                { // loop thru starters get name and odds
// get actual odds
                                                    // get actual odds
                                                    s = listResLine.get(E);
                                                    loc = s.lastIndexOf('.');
                                                    wkS1 = s.substring((loc-3),(loc + 4));
                                                    String wkS3 = wkS1.substring(0,2);
                                                    char c1 = wkS3.charAt(0);
                                                    char c2 = wkS3.charAt(1);
                                                    if ( c1 != ' '){ // c1 is a number
                                                        if ( c2 == ' '){// c2 is a space so c1 is not a real # replace it with ' '
                                                            wkS3 = "  "+wkS1.substring(2);
                                                            wkS1 = wkS3;

                                                        }
                                                    }
                                                    // get name

                                                    loc = s.indexOf("(");
                                                    --loc;
                                                    int nameStart =0;
                                                    int wkName =0;
                                                    wkName = s.indexOf(" ",4);
                                                    //wkS2 = s.substring(wkName, loc);

                                                    wkName = s.indexOf(" ",++wkName);
                                                    //wkS2 = s.substring(wkName, loc);
                                                    wkName = s.indexOf(" ",++wkName);
                                                    nameStart = s.indexOf(" ",++wkName);




                                                    wkS2 = s.substring(++nameStart, loc);
                                                      /*if(wkS2.charAt(1) == ' ') {  // needed for when days away is < 10
                                                          wkS2 = wkS2.substring(2);
                                                      }
                                                      else if(wkS2.charAt(1) == ' ')
                                                          wkS2 = wkS2.substring(2);
*/
                                                    // update  sbwkMl
                                                    // search array for horse name
                                                    char dq =' ';
                                                    char dq1=' ';
                                                    char dq2=' ';
                                                    if(wkS2.length() > 2) {
                                                        dq = wkS2.charAt(0);
                                                        dq1 = wkS2.charAt(1);
                                                        dq2 = wkS2.charAt(2);

                                                        if (dq2 == '-')
                                                            if (dq1 == 'Q')
                                                                if (dq == 'D')
                                                                    wkS2 = wkS2.substring(3);
                                                    }

                                                    int name = 0;
                                                    boolean testIT=false;
                                                    String nameFind=" ";
                                                    while(!sbwkML[name].equals("")){
                                                        nameFind = sbwkML[name].toString();
                                                        //int wkloc =   sbwkML[name].lastIndexOf(wkS2);
                                                        testIT = nameFind.contains(wkS2);

                                                        if(testIT){
                                                            sbwkML[name].append(wkS1);
                                                            break;
                                                            //name++;

                                                        } // end if
                                                        name++; // loop thru list
                                                    } // end while
                                                } // end for



                                                // loop until wps pool
                                                s = listResults.get(lpR++);
                                                while(!s.contains("Pool")){ // loop Result file until find wps Pool
                                                    s = listResults.get(lpR++);
                                                    if(s.contains("Disqual")){
                                                        // cnt++;
                                                        listCombined.add(s);
                                                    }
                                                    if(s.contains("Scratched")){
                                                        // cnt++;
                                                        listCombined.add(s);
                                                    }

                                                }

// listCombinded prior to Puting ML
                                                listCombined.add("Non-Sorted ML");

                                                while(!s.contains("Preview")){ // add result payoffs includes all exotics
                                                    listResLine.add(s);
                                                    s = listResults.get(lpR++);

                                                } // end while !Preview

                                                while(!s.contains("Copyright")){ // loop until line with weather
                                                    s = listResults.get(lpR++);
                                                }
                                                lpR++;// +1 to move lpR pnt to line with "Thoroughbred"

                                                // sort Morling line fav and actual odds fav
                                                // name and ML converted odds into an array
                                                Float wkF=0.00f;
                                                Float[] oddsML = new Float[entryML];
                                                Float[] oddsMLrankedsort = new Float[entryML]; // this will hold ranked copy of oddsML[]
                                                Float[] oddsMLrsortNoscr = new Float[actualStarters];
                                                Float[] actualOdds = new Float[entryML];
                                                String[] nameMLrankedsort = new String[entryML];
                                                String[] nameMLrsortNoscr = new String[actualStarters];

                                                String[] nameSortMLfinal = new String[actualStarters];
                                                Float[] oddsSortMLfinal = new Float[actualStarters];

                                                String[] nameML = new String[entryML];
                                                String[] nameML2 = new String[entryML];
                                                String[] ml_oddsVar = new String[entryML];
                                                String[] ml_oddsVarNoscr = new String[actualStarters];

                                                String nameWK="";
                                                for(int E=0; E < entryML; E++){
                                                    // get name
                                                    nameWK = sbwkML[E].toString();
                                                    int nameS = sbwkML[E].indexOf(",");
                                                    int nameE = sbwkML[E].lastIndexOf(",");
                                                    nameML[E] = sbwkML[E].substring(nameS+1,nameE).toString();

                                                    int oddsS = sbwkML[E].indexOf("~");
                                                    int oddsE = sbwkML[E].indexOf(":");
                                                    nameWK = nameWK.substring(oddsS+1,oddsE);
                                                    oddsML[E] = Float.parseFloat(nameWK);

                                                }// end for E
                                                // copy names in 2nd names array
                                                for(int E=0; E < entryML; E++){
                                                    nameML2[E] = nameML[E];
                                                }

                                                // sort ML favorites
                                                int n = nameML.length;
                                                Float temp = 0.0f;
                                                String tempS="";
                                                for(int t=0; t < n; t++){
                                                    for(int j=1; j < (n-t); j++){
                                                        if(oddsML[j-1] > oddsML[j]){
                                                            //swap elements
                                                            temp = oddsML[j-1];
                                                            tempS = nameML[j-1];
                                                            oddsML[j-1] = oddsML[j];
                                                            nameML[j-1] = nameML[j];
                                                            oddsML[j] = temp;
                                                            nameML[j] = tempS;
                                                        } // endif
                                                    } // end for j
                                                } // end for i
                                                // append ML fav to sbwkML

                                                for(int E=0; E < entryML; E++){
                                                    tempS = nameML[E];

                                                    int t = 0;

                                                    while( t < entryML){
                                                        nameWK = sbwkML[t].toString();
                                                        if(nameWK.contains(tempS)){
                                                            sbwkML[t].append(" MLrank ");
                                                            sbwkML[t].append(E+1);
                                                            sbwkML[t].append("# ");
                                                            break; // end while loop
                                                        } // end if
                                                        t++;
                                                    } // end while

                                                }  // end for E
                                                // nameML[0-#enteries] is the NAME sorded by ML odds
                                                // oddsML[0-#Enteries] is the float value of the MLodds  that is assigned to nameML[]
                                                // oddsML will be put in oddsML2

                                                // now sort actual odds
                                                int noSCR =0;
                                                String nameInfo;
                                                boolean NotSCR = true;
                                                for(int E=0; E < entryML; E++){
                                                    // get name

                                                    nameWK = nameML[E];// sbwkML[E].toString();
                                                    //int nameS = sbwkML[E].indexOf(",");
                                                    //int nameE = sbwkML[E].lastIndexOf(",");
                                                    Integer rank;
                                                    Integer rankNoscr;

                                                    // name&Odds are ranked ML

                                                    nameMLrankedsort[E] = nameML[E];
                                                    oddsMLrankedsort[E] = oddsML[E];
                                                    rank = E+1;
                                                    tempS = nameWK+"                    ";


//                                                    ml_oddsVar[E]=tempS+ " ( "+rank.toString()+") "; //+oddsML[E].toString());



                                                   // nameML[E] = nameML2[E]; // copied name earlier
                                                    //int oddsS = sbwkML[E].indexOf(":");
                                                   // int oddsE = sbwkML[E].indexOf(".",oddsS);

                                                    // find name in sbwkML if not scratch than add to ml_Odds... noSCR
                                                    NotSCR = true;
                                                    for(int i=0; i < entryML;i++){
                                                        nameInfo = sbwkML[i].toString();
                                                        if (nameInfo.contains(nameWK)){

                                                            if(nameInfo.contains("SCR")){
                                                                NotSCR = false;
                                                                break;
                                                            }
                                                            break;
                                                        }

                                                    }

                                                    if(NotSCR) {


                                                       // oddsML[E] = Float.parseFloat(nameWK);
                                                        oddsMLrsortNoscr[noSCR] = oddsML[E];
                                                        nameMLrsortNoscr[noSCR] = nameML[E];
                                                        oddsSortMLfinal[noSCR] = oddsML[E];
                                                        nameSortMLfinal[noSCR] = nameML[E];

                                                        rankNoscr = noSCR+1;
                                                        if(rankNoscr < 10) {
                                                            tempS = tempS.substring(0,20);
                                                        }
                                                        else {
                                                            tempS = tempS.substring(0, 19);
                                                        }
                                                        ml_oddsVarNoscr[noSCR++]=tempS + " ("+rankNoscr.toString()+")";  // ranked ML


                                                       // actualOdds[E] = oddsML[E];
                                                        //ml_oddsVar[E]+= "  "+oddsML[E].toString();
                                                    }
                                                    //else { // scratch
                                                      //  oddsML[E] = 98.76f;
                                                        //ml_oddsVar[E]+="  SRC";
                                                    //}

                                                } // end for get odds
                                                // get acutal odds of non SCR horses
                                                for(int E=0; E < entryML; E++) {
                                                    // get name

                                                    nameWK = nameML[E]; // sbwkML[E].toString();
                                                    for(int i=0; i < entryML;i++) {
                                                        nameInfo = sbwkML[i].toString();
                                                        if (nameInfo.contains(nameWK)) {
                                                            if (!nameInfo.contains("SCR")) {
                                                                int oddsS = sbwkML[i].indexOf(":");
                                                                int oddsE = sbwkML[i].indexOf(".", oddsS);
                                                                nameWK = sbwkML[i].substring(oddsS + 1, oddsE + 2);   // post position list
                                                                nameWK = nameWK.trim();
                                                                wkF = Float.parseFloat(nameWK);
                                                                wkF = getFloatOdds(wkF);
                                                                oddsML[E] = wkF;
                                                            } // end if !SCR
                                                            else {
                                                                oddsML[E]=987.65f;
                                                                break;
                                                            }
                                                            break;
                                                        } // end if nameWK
                                                    } // for i loop
                                                    nameInfo="";

                                                } // for loop E
// copy actual odds of MLsorted non-SCR
                                                noSCR=0;
                                                for(int i=0; i < entryML; i++){
                                                    if (oddsML[i] < 900.00f){
                                                        actualOdds[noSCR++] = oddsML[i]; // actul odds sorded by non-SCR ML names
                                                    }
                                                }

                                                // sort actual odds odds favorites
                                                n = nameML.length;
                                                temp = 0.0f;
                                                tempS="";
                                                for( int t=0; t < n; t++){
                                                    for(int j=1; j < (n-t); j++){
                                                        if(oddsML[j-1] > oddsML[j]){
                                                            //swap elements
                                                            temp = oddsML[j-1];
                                                            tempS = nameML[j-1];
                                                            oddsML[j-1] = oddsML[j];
                                                            nameML[j-1] = nameML[j];
                                                            oddsML[j] = temp;
                                                            nameML[j] = tempS;
                                                        } // endif
                                                    } // end for j
                                                } // end for i
                                                //
                                                // oddsML = actual odds sorted
                                                // nameML = name of actual odds sorted

                                                // copy contents
                                                for(int E=0; E < actualStarters; E++) {
                                                    // copy name and odds name
                                                    oddsMLrsortNoscr[E]=oddsML[E];
                                                    nameMLrsortNoscr[E]=nameML[E];

                                                }


                                                // sort non-scr odds favorites
                                                n = nameMLrsortNoscr.length;
                                                temp = 0.0f;
                                                tempS="";
                                                for( int t=0; t < n; t++){
                                                    for(int j=1; j < (n-t); j++){
                                                        if(oddsMLrsortNoscr[j-1] > oddsMLrsortNoscr[j]){
                                                            //swap elements
                                                            temp = oddsMLrsortNoscr[j-1];
                                                            tempS = nameMLrsortNoscr[j-1];
                                                            oddsMLrsortNoscr[j-1] = oddsMLrsortNoscr[j];
                                                            nameMLrsortNoscr[j-1] = nameMLrsortNoscr[j];
                                                            oddsMLrsortNoscr[j] = temp;
                                                            nameMLrsortNoscr[j] = tempS;
                                                        } // endif
                                                    } // end for j
                                                } // end for i
                                                //
                                                // append ActualOdds fav to sbwkML

                                                noSCR=0;
                                                int t=0;
                                                for(int E=0;E < actualStarters;E++){
                                                    tempS = nameMLrsortNoscr[E]; // names by actual odds sort

                                                    t = 0;
                                                    while( t < actualStarters){
                                                        //tempS = nameML[E];
                                                        if(ml_oddsVarNoscr[t].contains(tempS)){
                                                            Integer rank = E +1;
                                                            //sbwkML[t].append(" ODDSrank ");
                                                            //sbwkML[t].append(E+1);
                                                            ml_oddsVarNoscr[t] += " "+ rank.toString();
                                                           // actualOdds[t] = oddsMLrsortNoscr[E]; // odds by related namse  sort by ML no scr
                                                            break; // end while loop
                                                        } // end if

                                                        t++;
                                                    } // end while


                                                }
                                                int postedWPS=0;

                                                for(int E=0; E < entryML; E++){
                                                    tempS = nameML[E]; // tempS will be name by actual odds rank [E]
                                                    Integer rank =E+1;

                                                    t = 0; // update sbwkML
                                                    while( t < entryML){
                                                        nameWK = sbwkML[t].toString();
                                                        if(nameWK.contains(tempS)){
                                                            if(!nameWK.contains("SCR")) {
                                                                noSCR++;
                                                                sbwkML[t].append(" ODDSrank ");
                                                                sbwkML[t].append(noSCR);
                                                                // loop thru wps name to append payoffs
                                                                if (postedWPS < 3) { // if win ,place show are found than no need to check
                                                                    //

                                                                    // loop w,p,s
                                                                    if (nameWK.contains(wps[1][0])) {

                                                                        sbwkML[t].append(" W! ");
                                                                        if (!wps[1][1].contains("0.00"))
                                                                            sbwkML[t].append(wps[1][1]);
                                                                        sbwkML[t].append(" P! ");
                                                                        if (!wps[1][2].contains("0.00"))
                                                                            sbwkML[t].append(wps[1][2]);
                                                                        sbwkML[t].append(" S! ");
                                                                        if (!wps[1][3].contains("0.00"))
                                                                            sbwkML[t].append(wps[1][3]);
                                                                        postedWPS++; // found winner
                                                                        //break;
                                                                    } // if wps[1][0]
                                                                    else if(nameWK.contains(wps[2][0])) {

                                                                        sbwkML[t].append(" W! ");
                                                                        if (!wps[2][1].contains("0.00"))
                                                                            sbwkML[t].append(wps[2][1]);
                                                                        sbwkML[t].append(" P! ");
                                                                        if (!wps[2][2].contains("0.00"))
                                                                            sbwkML[t].append(wps[2][2]);
                                                                        sbwkML[t].append(" S! ");
                                                                        if (!wps[2][3].contains("0.00"))
                                                                            sbwkML[t].append(wps[2][3]);
                                                                        postedWPS++; // found place
                                                                        // break;
                                                                    } // end if [2][0]
                                                                    else if(nameWK.contains(wps[3][0])) {

                                                                        sbwkML[t].append(" W! ");
                                                                        if (!wps[3][1].contains("0.00"))
                                                                            sbwkML[t].append(wps[3][1]);
                                                                        sbwkML[t].append(" P! ");
                                                                        if (!wps[3][2].contains("0.00"))
                                                                            sbwkML[t].append(wps[3][2]);
                                                                        sbwkML[t].append(" S! ");
                                                                        if (!wps[3][3].contains("0.00"))
                                                                            sbwkML[t].append(wps[3][3]);
                                                                        postedWPS++; // found show
                                                                    } // end if [3][0]

                                                                } // end if postedWPS


                                                            } // end if !SCR
                                                            t=entryML + 1; // end while loop
                                                        } // end if

                                                        t++;
                                                    } // end while

                                                }  // end for E

                                                for(int E=0; E < entryML; E++) {
                                                    listCombined.add(sbwkML[E].toString());
                                                }
                                                postedWPS=0;
                                                // add ML with actual odds to listCombind
                                                for(int E=0; E < actualStarters; E++){
                                                                                             //  listCombined.add(sbwkML[E].toString());
                                                    // complete dbl ML strings
                                                    // add ML odds Actual odds and dbl calculation
                                                   // if(!ml_oddsVar[E].contains("SCR")) {
                                                        ml_oddsVarNoscr[E] += " ML " + oddsSortMLfinal[E].toString();
                                                        ml_oddsVarNoscr[E] += "  " + actualOdds[E].toString();
                                                        Float tempF = (actualOdds[E]) / oddsSortMLfinal[E];
                                                        tempS = tempF.toString()+"00";

                                                        wk = tempS.indexOf('.');
                                                        tempS = "  " + tempS.substring(0, (wk + 3));
                                                        ;
                                                        ml_oddsVarNoscr[E] += tempS;

                                                    if (postedWPS < 3) { // if win ,place show are found than no need to check
                                                        //

                                                        // loop w,p,s
                                                        if (ml_oddsVarNoscr[E].contains(wps[1][0])) {

                                                            ml_oddsVarNoscr[E]+=" W! ";
                                                            if (!wps[1][1].contains("0.00"))
                                                                ml_oddsVarNoscr[E]+=wps[1][1];
                                                            ml_oddsVarNoscr[E]+=" P! ";
                                                            if (!wps[1][2].contains("0.00"))
                                                                ml_oddsVarNoscr[E]+=wps[1][2];
                                                            ml_oddsVarNoscr[E]+=" S! ";
                                                            if (!wps[1][3].contains("0.00"))
                                                                ml_oddsVarNoscr[E]+=wps[1][3];
                                                            postedWPS++; // found winner
                                                            //break;
                                                        } // if wps[1][0]
                                                        else if(ml_oddsVarNoscr[E].contains(wps[2][0])) {

                                                            ml_oddsVarNoscr[E]+=" W! ";
                                                            if (!wps[2][1].contains("0.00"))
                                                                ml_oddsVarNoscr[E]+=wps[2][1];
                                                            ml_oddsVarNoscr[E]+=" P! ";
                                                            if (!wps[2][2].contains("0.00"))
                                                                ml_oddsVarNoscr[E]+=wps[2][2];
                                                            ml_oddsVarNoscr[E]+=" S! ";
                                                            if (!wps[2][3].contains("0.00"))
                                                                ml_oddsVarNoscr[E]+=wps[2][3];
                                                            postedWPS++; // found place
                                                            // break;
                                                        } // end if [2][0]
                                                        else if(ml_oddsVarNoscr[E].contains(wps[3][0])) {

                                                            ml_oddsVarNoscr[E]+=" W! ";
                                                            if (!wps[3][1].contains("0.00"))
                                                                ml_oddsVarNoscr[E]+=wps[3][1];
                                                            ml_oddsVarNoscr[E]+=" P! ";
                                                            if (!wps[3][2].contains("0.00"))
                                                                ml_oddsVarNoscr[E]+=wps[3][2];
                                                            ml_oddsVarNoscr[E]+=" S! ";
                                                            if (!wps[3][3].contains("0.00"))
                                                                ml_oddsVarNoscr[E]+=wps[3][3];
                                                            postedWPS++; // found show
                                                        } // end if [3][0]

                                                    } // end if postedWPS


                                                    tempF = 0.00f;
                                                   // } // end if
                                                } // end for




                                                wk--;
                                                // test if end of result file


                                                resultsFolderPath += String.valueOf(actualStarters)+".txt";
                                                System.out.println(resultsFolderPath);

                                                File cfout = new File(resultsFolderPath); // Converted file
                                                try { // the following saves the completed listCombined of the individual races
                                                    // listCombined is ArrayList of combined files
                                                    PrintWriter output = new PrintWriter(cfout);
                                                    for (int line = 0; line < listCombined.size(); line++) {
                                                        output.println(listCombined.get(line)); // this works 09/01/20
                                                    } // end for line

                                                    output.println("Sort by ML  (ML rank) Odds rank  ML Odds Ratio");

                                                    for (int line = 0; line < ml_oddsVarNoscr.length; line++) {
                                                        output.println(ml_oddsVarNoscr[line]); // this works 09/01/20
                                                    } // end for line
                                                    output.println("Finish Results");

                                                    for (int line = 0; line < listResLine.size(); line++) {
                                                        output.println(listResLine.get(line)); // this works 09/01/20
                                                    } // end for line
                                                    output.close();
                                                } catch (IOException e) {
                                                    e.printStackTrace();
                                                }
                                                // reset sbwk to all = ""
                                                //for (int sb =0; sb < 20; sb++){
                                                ///  sbwkML[sb].equals("");
                                                //}
                                                for (int sbML = 0; sbML < sbwkML.length; sbML++) {
                                                    sbwkML[sbML] = new StringBuilder();
                                                }
                                                if( lpR == listRsize){
                                                    // no more races
                                                    numRace = 20;
                                                    lp = (listMLsize + 10);
                                                    cnt++;
                                                }



                                                cnt1++; // debug only

                                                //folder = new File(folderPath);
                                                cnt1++;
                                                // debug break out of loop
                                                // break;
                                            } // end for numRace





                                        } // end while will loop thru all listML



                                } // end   else if typeML =2
                                else { // results file only
////////////////////// RESULTS ONLY NO ML ///////////////////////////////////////////// RESULTS ONLY //////////
                                    for (int numRace = 1; numRace <= 16; numRace++) {
                                        // put in track and date
                                        // test for no more races
                                        listCombined.clear();
                                        listCombined.add(raceDate);
                                        listCombined.add(track);

                                        // update file name
                                        resultsFolderPath = resultsFolderPathPrefix+String.valueOf(numRace) + "_";
                                        //get track grade
                                        for (int g = 0; g < gradeTrackLen; g++) {
                                            if (track.equals(gradeTrack[g][0])) { // comp string vals of track name
                                                wkS = gradeTrack[g][1]+"_"; // wkS is a work String var
                                                break; // out of for loop
                                            }
                                        } // end for g : get track grade

                                        // get race type from listResults
                                        String s = "";
                                        String wkS1 =""; // work string
                                        String wkS2="";

                                        listResLine.clear(); // clear after race # loop

                                        s = listResults.get(lpR++);
                                        // search contents of current line
                                        // check for cancelled
                                        if(s.contains("Cancelled")) {
                                            numRace = 20;
                                            //break;
                                           break;
                                        }

                                        wkS += getConditions(s); // changed from += to  =
                                        // get sex and age
                                        wkS += "_";
                                        String age = "";
                                        s = listResults.get(lpR++);
                                        wkS += getAgeSex(s);
                                        // loop until you get to line with Record:
                                        while(!s.contains("Record:")){
                                            s = listResults.get(lpR++);
                                        }
                                        // line contailes distance
                                        wkS += getDistanceSurface(s);
                                        while(!s.contains("Weather:")){ // loop until line with weather
                                            s = listResults.get(lpR++);
                                        }
                                        wkS += getSurfaceCondition(s);
                                        // get winners and put in wps string
                                        wpsP = lpR;
                                        wpsS = s;
                                        while(!wpsS.contains("ace Sho")){ // loop until line with weather
                                            wpsS = listResults.get(wpsP++);
                                        } // loop until w p s header
                                        wps[1][0]=""; wps[1][1]="0.00"; wps[1][2]="0.00"; wps[1][3]="0.00";
                                        wps[2][0]=""; wps[2][1]="0.00"; wps[2][2]="0.00"; wps[2][3]="0.00";
                                        wps[3][0]=""; wps[3][1]="0.00"; wps[3][2]="0.00"; wps[3][3]="0.00";

                                        //Note need to test for no show bets and
                                        // winner
                                        wpsS = listResults.get(wpsP++);
                                        wpsS = getNameWin(wpsS);
                                        wk1=wpsS.indexOf(',');
                                        wk2=wpsS.indexOf(',',wk1+1);
                                        wps[1][0]=wpsS.substring(++wk1,wk2); // name
                                        wk3=wpsS.indexOf(',',++wk2);
                                        wps[1][1]=wpsS.substring(wk2,wk3); // win price
                                        boolean winShow = true;
                                        if(wpsS.indexOf(',',wk3+1) > 0) {
                                            // this means both place and show payoffs
                                            wk1 = wpsS.indexOf(',',++wk3);
                                            wps[1][2]=wpsS.substring(wk3,wk1); // place
                                            wps[1][3]=wpsS.substring(++wk1); // show


                                        }
                                        else { // only place price
                                            // wps[1][2]=wpsS.substring(wk3,wk1);
                                            wps[1][2]=wpsS.substring(++wk3); // place
                                            winShow = false;
                                        }

// 2nd horse
                                        wpsS = listResults.get(wpsP++);
                                        wpsS = getNameWin(wpsS);
                                        wk1=wpsS.indexOf(',');
                                        wk2=wpsS.indexOf(',',wk1+1);
                                        wps[2][0]=wpsS.substring(++wk1,wk2); // name
                                        // is it dead-heat
                                        wkd1 = wpsS.indexOf('.');
                                        wkd2 = wpsS.indexOf('.',wkd1+1);
                                        if(wkd2 > 0)
                                            wkd3 = wpsS.indexOf('.',wkd2+1);
                                        else
                                            wkd3 = -1;
                                        if(wkd3 > 0){
                                            // win deadheat
                                            // same code as winner
                                            wk3=wpsS.indexOf(',',++wk2);
                                            wps[2][1]=wpsS.substring(wk2,wk3); // win price

                                            if(wpsS.indexOf(',',wk3+1) > 0) {
                                                // this means both place and show payoffs
                                                wk1 = wpsS.indexOf(',',++wk3);
                                                wps[2][2]=wpsS.substring(wk3,wk1); // place
                                                wps[2][3]=wpsS.substring(++wk1); // show
                                            }


                                        } // end if wkd3 > 0
                                        else if(winShow){
                                            // 2nd place winner has place and show price
                                            // get place price
                                            wk3=wpsS.indexOf(',',++wk2);
                                            wps[2][2]=wpsS.substring(wk2,wk3); // place price
                                            //  wk1 = wpsS.indexOf(',',++wk3);
                                            wps[2][3]=wpsS.substring(++wk3); // show

                                        }
                                        else{
                                            // place only payoff
                                            // wk3=wpsS.indexOf(',',++wk2);
                                            wps[2][2]=wpsS.substring(++wk2); // place price
                                        }
// 3rd horse
                                        wpsS = listResults.get(wpsP++);
                                        wpsS = getNameWin(wpsS);
                                        wk1=wpsS.indexOf(',');
                                        wk2=wpsS.indexOf(',',wk1+1);
                                        if (wk2 >0)//has a show price
                                            wps[3][0]=wpsS.substring(++wk1,wk2);// name
                                        else
                                            wps[3][0]=wpsS.substring(++wk1);// name
                                        // is it dead-heat
                                        wkd1 = wpsS.indexOf('.');
                                        wkd2 = wpsS.indexOf('.',wkd1+1);
                                        if(wkd2 > 0)
                                            wkd3 = wpsS.indexOf('.',wkd2+1);
                                        else
                                            wkd3 = -1;
                                        if(wkd3 > 0){
                                            // win deadheat
                                            // same code as winner
                                            wk3=wpsS.indexOf(',',++wk2);
                                            wps[3][1]=wpsS.substring(wk2,wk3); // win price

                                            if(wpsS.indexOf(',',wk3+1) > 0) {
                                                // this means both place and show payoffs
                                                wk1 = wpsS.indexOf(',',++wk3);
                                                wps[3][2]=wpsS.substring(wk3,wk1); // place
                                                wps[3][3]=wpsS.substring(++wk1); // show
                                            }



                                        }
                                        else if(wkd2 > 0){
                                            // place deadheat
                                            wk3=wpsS.indexOf(',',++wk2);
                                            wps[3][2]=wpsS.substring(wk2,wk3); // place price
                                            //  wk1 = wpsS.indexOf(',',++wk3);
                                            wps[3][3]=wpsS.substring(++wk3); // show

                                        }
                                        else if(winShow){
                                            // Show price only
                                            wps[3][3]=wpsS.substring(++wk2); // show

                                        }
                                        // note if no winShow than there was no show betting in this race


                                        // at this point wkS should equal what race type and conditions
                                        //resultsFolderPath += wkS;

                                          /*  try { // put string of race date in Date format

                                                d1 = df1.parse(raceDate);
                                               // System.out.println("Date in dd/MM/yyyy format is: " + df1.format(d1));

                                            } catch (Exception ex) {
                                                System.out.println(ex);
                                            }
*/


//                                           Get odds from result charts Note scratches can effect horse # - search by name
                                        s = listResults.get(lpR++);
                                        while(!s.contains("Pgm")){ // loop until line with
                                            s = listResults.get(lpR++);

                                        } // loop thru junk info

                                        s = listResults.get(lpR++);
                                        // should be a first finisher of race
                                        entryML =0;

                                        while(!s.contains("Times:")) { // Result file looping to Split Times: line


                                            StringBuilder sb = new StringBuilder();


                                            // get date Note: 1st time  starts date r();
                                            sb.append(getDaysWay(s,raceDate));



                                            // get new line before looping
                                            listResLine.add(sb.toString());
                                            // get name

                                            loc = s.indexOf("(");
                                            --loc;
                                            boolean NoScr = true;
                                            int scrL = s.indexOf("---");
                                            if( s.contains("--- ") && scrL < 4 )
                                                NoScr = false;
                                            int nameStart =0;
                                            int wkName =0;

                                            if(NoScr){
                                                wkName = s.indexOf(" ", 4); // before last race info
                                                nameStart = s.indexOf(" ", ++wkName); // before name
                                            }
                                            else { // hourse scratched
                                            nameStart = 3;
                                            }





                                            wkS2 = s.substring(++nameStart, loc);
                                            sbwkML[entryML++].append(wkS2);



                                            s = listResults.get(lpR++);
                                        }// end while not times
                                        int actualStarters = listResLine.size();
                                        wkS += "99_"+String.valueOf(actualStarters);

                                        // at this point wkS should equal what race type and conditions and how many
                                        resultsFolderPath += wkS;
                                        // add race conditions plus # of entries to listCombined
                                        listCombined.add(wkS);
                                     //   listCombined.add("Odds Ranking"); //
                                        if(numRace == 8){
                                            cnt1++; // debug
                                        }
                                        Float odd1;
                                        Float odd2;

                                        for ( int E=0; E < actualStarters; E++) { // loop thru starters get name and odds
                                            // get actual odds
                                            s = listResLine.get(E);
                                            loc = s.lastIndexOf('.');
                                            wkS1 = s.substring((loc - 3), (loc + 4));
                                            String wkS3 = wkS1.substring(0, 2);
                                            char c1 = wkS3.charAt(0);
                                            char c2 = wkS3.charAt(1);
                                            if (c1 != ' ') { // c1 is a number
                                                if (c2 == ' ') {// c2 is a space so c1 is not a real # replace it with ' '
                                                    wkS3 = "  " + wkS1.substring(2);
                                                    wkS1 = wkS3;

                                                }
                                            } // end if c1
                                            // add odds to sbwk
                                            wkS1.trim();
                                            wk=wkS1.lastIndexOf('*');
                                            if(wk > 0)
                                                wkS1 = wkS1.substring(0,wk);

                                            odd1 = Float.parseFloat(wkS1);

                                            odd2 = getFloatOdds(odd1);
                                            wkS1 = " "+Float.toString(odd2);
                                            sbwkML[E].append(wkS1);
                                        } // end for loop E









                                        // loop until wps pool
                                        s = listResults.get(lpR++);
                                        while(!s.contains("Pool")){ // loop Result file until find wps Pool
                                            s = listResults.get(lpR++);
                                            if(s.contains("Disqual")){
                                                // cnt++;
                                                listCombined.add(s);
                                            }
                                            if(s.contains("Scratched")){
                                                // cnt++;
                                                listCombined.add(s);
                                            }

                                        }
                                        listCombined.add("Odds Ranking"); //

                                        while(!s.contains("Preview")){ // add result payoffs includes all exotics
                                            listResLine.add(s);
                                            s = listResults.get(lpR++);

                                        } // end while !Preview

                                        while(!s.contains("Copyright")){ // loop until line with weather
                                            s = listResults.get(lpR++);
                                        }
                                        lpR++;// +1 to move lpR pnt to line with "Thoroughbred"

                                        // sort Morling line fav and actual odds fav
                                        // name and ML converted odds into an array
                                        Float[] oddsML = new Float[entryML];
                                        String[] nameML = new String[entryML];
                                        String[] nameML2 = new String[entryML];
                                        String nameWK="";
                                        //Float temp = 0.0f;
                                        //String tempS="";

/*                                        for(int E=0; E < entryML; E++){
                                            // get name
                                            nameWK = sbwkML[E].toString();
                                            int nameS = sbwkML[E].indexOf(",");
                                            int nameE = sbwkML[E].lastIndexOf(",");
                                            nameML[E] = sbwkML[E].substring(nameS+1,nameE).toString();
                                            int oddsS = sbwkML[E].indexOf("~");
                                            int oddsE = sbwkML[E].indexOf(":");
                                            nameWK = nameWK.substring(oddsS+1,oddsE);
                                            oddsML[E] = Float.parseFloat(nameWK);

                                        }// end for E
                                        // copy names in 2nd names array
                                        for(int E=0; E < entryML; E++){
                                            nameML2[E] = nameML[E];
                                        }
*/
                                        // sort by PP
                                        int n = actualStarters;
                                        Float temp = 0.0f;
                                        int pp1 =0;
                                        int pp2 =0;
                                        int cVal1 =0;
                                        int cVal2 =0;
                                        char[] cVal = {'0','0'};
                                        String tempS="";
                                        String tempS2="";
                                        for(int t=0; t < n; t++){
                                            for(int j=1; j < (n-t); j++){
                                                tempS = sbwkML[j-1].toString().substring(0,2).trim();
                                                tempS2 = sbwkML[j].toString().substring(0,2).trim();
                                                if (tempS.length() > 1) {
                                                    cVal = tempS.toCharArray();
                                                   // cVal1 = Integer.;
                                                }

/*
                                                if (tempS == "r1")
                                                    break;
                                                if (tempS2 == "r1")
                                                    break;
*/
                                                try {
                                                    pp1 = Integer.parseInt(tempS);
                                                } catch (NumberFormatException e) {
                                                    pp1 = Integer.parseInt(tempS.substring(0,1));
                                                    cVal1=0; // debug
                                                }
                                                try {
                                                    pp2 = Integer.parseInt(tempS2);
                                                } catch (NumberFormatException e) {
                                                    pp2 = Integer.parseInt(tempS2.substring(0,1));
                                                    cVal1=0; // debug
                                                }

                                                //pp2 = Integer.parseInt(tempS2);
                                                if(pp1 > pp2){
                                                    //swap elements
                                                    sbwkML[19] = sbwkML[j-1];
                                                    //tempS = nameML[j-1];
                                                    sbwkML[j-1] = sbwkML[j];
                                                    //nameML[j-1] = nameML[j];
                                                    sbwkML[j] = sbwkML[19];
                                                    //nameML[j] = tempS;
                                                } // endif
                                            } // end for j
                                        } // end for t

                                        // sort by odds
                                        n = actualStarters;



                                        //String tempS="";
                                        //String tempS2="";
                                        for(int t=0; t < n; t++){
                                            for(int j=1; j < (n-t); j++){
                                                int oddsS = sbwkML[j-1].lastIndexOf(".");
                                                int oddsE = sbwkML[j].lastIndexOf(".");

                                                tempS = sbwkML[j-1].toString().substring(oddsS - 2, oddsS + 2).trim();
                                                tempS2 = sbwkML[j].toString().substring(oddsE - 2, oddsE + 2).trim();
                                                odd1 = Float.parseFloat(tempS);
                                                odd2 = Float.parseFloat(tempS2);
                                                //pp1 = Integer.parseInt(tempS);
                                                //pp2 = Integer.parseInt(tempS2);
                                                if(odd1 > odd2){
                                                    //swap elements
                                                    sbwkML[19] = sbwkML[j-1];
                                                    //tempS = nameML[j-1];
                                                    sbwkML[j-1] = sbwkML[j];
                                                    //nameML[j-1] = nameML[j];
                                                    sbwkML[j] = sbwkML[19];
                                                    //nameML[j] = tempS;
                                                } // endif
                                            } // end for j
                                        } // end for t
                                        int postedWPS = 0;
                                        int longestName = 0;
                                        for (int E=0; E < actualStarters; E++) {
                                            // look thru every name and add winner payoffs
                                            wk = sbwkML[E].length();
                                            if(wk > longestName) longestName = wk;
                                        }

                                        for (int E=0; E < actualStarters; E++){
                                            // look thru every name and add winner payoffs
                                            wkS = sbwkML[E].toString();
                                            wkS1="";
                                            wk = wkS.length();
                                            if (wk < longestName){
                                                sbwkML[E].setLength(longestName);
                                            }
                                            if (postedWPS < 3) { // if win ,place show are found than no need to check
                                                //

                                                // loop w,p,s

                                                if (wkS.contains(wps[1][0])) {

                                                    wkS1 = " W! ";
                                                    if (!wps[1][1].contains("0.00"))
                                                        wkS1+=wps[1][1];
                                                    wkS1+=" P! ";
                                                    if (!wps[1][2].contains("0.00"))
                                                        wkS1+=wps[1][2];
                                                    wkS1+=" S! ";
                                                    if (!wps[1][3].contains("0.00"))
                                                        wkS1+=wps[1][3];
                                                    sbwkML[E].append(wkS1);
                                                    postedWPS++; // found winner
                                                    //break;
                                                } // if wps[1][0]
                                                else if(wkS.contains(wps[2][0])) {

                                                    wkS1 = " W! ";
                                                    if (!wps[2][1].contains("0.00"))
                                                        wkS1+=wps[2][1];
                                                    wkS1+=" P! ";
                                                    if (!wps[2][2].contains("0.00"))
                                                        wkS1+=wps[2][2];
                                                    wkS1+=" S! ";
                                                    if (!wps[2][3].contains("0.00"))
                                                        wkS1+=wps[2][3];
                                                    sbwkML[E].append(wkS1);


                                                    postedWPS++; // found place
                                                    // break;
                                                } // end if [2][0]
                                                else if(wkS.contains(wps[3][0])) {

                                                    wkS1 = " W! ";
                                                    if (!wps[3][1].contains("0.00"))
                                                        wkS1+=wps[3][1];
                                                    wkS1+=" P! ";
                                                    if (!wps[3][2].contains("0.00"))
                                                        wkS1+=wps[3][2];
                                                    wkS1+=" S! ";
                                                    if (!wps[3][3].contains("0.00"))
                                                        wkS1+=wps[3][3];
                                                    sbwkML[E].append(wkS1);


                                                    postedWPS++; // found show
                                                } // end if [3][0]

                                            } // end if postedWPS
                                        } // end for E < actualStaters



                                        // append ML fav to sbwkML

// Sort by PP
/*                                        for(int E=0; E < entryML; E++){
                                            tempS = nameML[E];

                                            int t = 0;
                                            while( t < entryML){
                                                nameWK = sbwkML[t].toString();
                                                if(nameWK.contains(tempS)){
                                                    sbwkML[t].append(" MLrank ");
                                                    sbwkML[t].append(E+1);
                                                    break; // end while loop
                                                } // end if
                                                t++;
                                            } // end while

                                        }  // end for E

                                        // now sort actual odds
                                        for(int E=0; E < entryML; E++){
                                            // get name
                                            nameWK = sbwkML[E].toString();
                                            //int nameS = sbwkML[E].indexOf(",");
                                            //int nameE = sbwkML[E].lastIndexOf(",");
                                            nameML[E] = nameML2[E]; // copied name earlier
                                            int oddsS = sbwkML[E].indexOf(":");
                                            int oddsE = sbwkML[E].indexOf(".",oddsS);
                                            if(oddsE != -1) {
                                                nameWK = nameWK.substring(oddsS + 1, oddsE + 2);
                                                nameWK = nameWK.trim();
                                                oddsML[E] = Float.parseFloat(nameWK);
                                            }
                                            else { // scratch
                                                oddsML[E] = 98.76f;
                                            }
                                        } // end for
                                        // sort odds favorites
                                        n = nameML.length;
                                        temp = 0.0f;
                                        tempS="";
                                        for( int t=0; t < n; t++){
                                            for(int j=1; j < (n-t); j++){
                                                if(oddsML[j-1] > oddsML[j]){
                                                    //swap elements
                                                    temp = oddsML[j-1];
                                                    tempS = nameML[j-1];
                                                    oddsML[j-1] = oddsML[j];
                                                    nameML[j-1] = nameML[j];
                                                    oddsML[j] = temp;
                                                    nameML[j] = tempS;
                                                } // endif
                                            } // end for j
                                        } // end for i
                                        //
                                        // append ActualOdds fav to sbwkML

                                        for(int E=0; E < entryML; E++){
                                            tempS = nameML[E];

                                            int t = 0;
                                            while( t < entryML){
                                                nameWK = sbwkML[t].toString();
                                                if(nameWK.contains(tempS)){
                                                    sbwkML[t].append(" ODDSrank ");
                                                    sbwkML[t].append(E+1);
                                                    break; // end while loop
                                                } // end if
                                                t++;
                                            } // end while

                                        }  // end for E

 */



                                        wk--;
                                        // test if end of result file


                                        resultsFolderPath += ".txt";
                                        System.out.println(resultsFolderPath);

                                        File cfout = new File(resultsFolderPath); // Converted file
                                        try { // the following saves the completed listCombined of the individual races
                                            // listCombined is ArrayList of combined files
                                            PrintWriter output = new PrintWriter(cfout);
                                            for (int line = 0; line < listCombined.size(); line++) {
                                                output.println(listCombined.get(line)); // this works 09/01/20
                                            } // end for line
                                            for (int line = 0; line < actualStarters; line++) {
                                                output.println(sbwkML[line].toString()); //
                                            } // end for line
                                            output.println("Finish Results");

                                            for (int line = 0; line < listResLine.size(); line++) {
                                                output.println(listResLine.get(line)); // this works 09/01/20
                                            } // end for line
                                            output.close();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                        for (int sbML = 0; sbML < sbwkML.length; sbML++) {
                                            sbwkML[sbML] = new StringBuilder();
                                        }

                                        if( lpR == listRsize){
                                            // no more races
                                            numRace = 20;

                                            cnt++;
                                        }



                                        cnt1++; // debug only

                                        //folder = new File(folderPath);
                                        cnt1++;
                                        // debug break out of loop
                                        // break;

                                    } // end for numRace




                                    cnt1++;
                                } // end else only convert Result file

                                /*resultsFolderPath += ".txt";
                                System.out.println(resultsFolderPath);

                                File cfout = new File(resultsFolderPath); // Converted file
                                try { // the following saves the completed listCombined of the individual races
                                    // listCombined is ArrayList of combined files
                                    PrintWriter output = new PrintWriter(cfout);
                                    for (int line = 0; line < listCombined.size(); line++) {
                                        output.println(listCombined.get(line)); // this works 09/01/20
                                    } // end for line

                                    for (int line = 0; line < listResLine.size(); line++) {
                                        output.println(listResLine.get(line)); // this works 09/01/20
                                    } // end for line
                                    output.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
*/

                                    cnt1++; // debug only
                                // clear out old listML info pervents proccing old info
                                listML.clear(); //
                                }
                                //folder = new File(folderPath);

                                cnt1++;
                        } // end for loop thru file names Sublistof files

                            //} //else System.out.println("There is no Folder @ given path :" + folderPath);
                    } else System.out.println("There is no Folder @ given path :" + folderPath);

                } // for loop - list of folders each track
            } // folder is a directory true




    } // end main

    private static Float getFloatOdds(Float wkF) {
        // nest if statements to put odds at tot-board values
        if( wkF == 1.10f){
            wkF = 1.0f;
        }
        else if(wkF == 1.3f){
            wkF = 1.2f;
        }
        else if (wkF == 1.9f){
            wkF=1.8f;
        }
        else if(wkF == 1.7f){
            wkF=1.6f;
        }
        else if (wkF > 1.9f && wkF < 2.5f) {
            wkF = 2.0f;
        }// end if >2 < 2.5
        else if (wkF > 2.4f && wkF < 3.0f) {
            wkF = 2.5f;
        }//
        else if (wkF > 2.9f && wkF < 3.5f) {
            wkF = 3.0f;
        }//
        else if (wkF > 3.4f && wkF < 4.0f) {
            wkF = 3.5f;
        }//
        else if (wkF > 3.9f && wkF < 4.5f) {
            wkF = 4.0f;
        }//
        else if (wkF > 4.4f && wkF < 5.0f) {
            wkF = 4.5f;
        }//
        else if (wkF > 4.9f && wkF < 6.0f) {
            wkF = 5.0f;
        }//
        else if (wkF > 5.9f && wkF < 7.0f) {
            wkF = 6.0f;
        }//
        else if (wkF > 6.9f && wkF < 8.0f) {
            wkF = 7.0f;
        }//
        else if (wkF > 7.9f && wkF < 9.0f) {
            wkF = 8.0f;
        }//

        else if (wkF > 8.9f && wkF < 10.0f) {
            wkF = 9.0f;
        }//


        return wkF;

    } // end methos get FloatOdds


} // end Main class
