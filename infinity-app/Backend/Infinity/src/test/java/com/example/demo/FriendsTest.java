//package com.example.demo;
//
//import com.example.demo.friends.Friends;
//import com.example.demo.friends.FriendsRepository;
//import com.example.demo.friends.FriendsService;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.junit.runners.Parameterized;
//import org.junit.runners.Parameterized.Parameters;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.Scanner;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertNotNull;
//
//
//@RunWith(Parameterized.class)
//public class FriendsTest
//{
//
//    private long id;
//    private long userrequest;
//    private long userreciever;
//    private boolean accepted;
//    private boolean expectApproved;
//    private int testCase;
//    private String expectedMessage;
//
//
//    @Autowired
//    private FriendsService friendsService;
//    @Autowired
//    private FriendsRepository friendsRepository;
//
//    public FriendsTest(long id, long userrequest, long userreciever, boolean accepted,int testCase, boolean expectApproved, String expectedMessage)
//    {
//        this.id = id;
//        this.accepted=accepted;
//        this.testCase=testCase;
//        this.expectApproved=expectApproved;
//        this.userrequest=userrequest;
//        this.userreciever=userreciever;
//        this.expectedMessage=expectedMessage;
//    }
//    @Parameters
//    public static Collection<Object[]> getParameters() {
//        Collection<Object[]> ParameterList = new ArrayList<Object[]>();
//        try {
//            Scanner in = new Scanner(new File("TestCases\\FriendsFile.txt"));
//            // Read as many lines as there are in the file
//            while (in.hasNextLine()) {
//                String l = in.nextLine();
//                // split the line using delimiter and then create the test-case object
//                String dataArray[] = l.split(",");
//                Object[] d = new Object[7];
//
//                d[0] = Long.parseLong(dataArray[0]); // id
//                d[1] = dataArray[1]; // userreceiver
//                d[2] = dataArray[2]; // userrequester
//                d[3] = dataArray[3]; // accepted
//                d[4] = dataArray[4]; // testCase
//                d[5] = dataArray[5]; // expectApproved
//                d[6] = Integer.parseInt(dataArray[6]); // expectedMessage
//                d[7] = Boolean.parseBoolean(dataArray[7]); // expectApproved
//                // add the test data into the arraylist
//                ParameterList.add(d);
//            } // end of while
//            in.close();
//
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        // return all the test-cases
//        return ParameterList;
//    }
//
//    @Test
//    public void testFriends() throws Throwable
//    {
//        Friends underTest = new Friends(userreciever, userrequest);
//
//        ConfirmedResponse result = underTest.ConfirmedFriends(testCase, id, userreciever, userrequest, accepted);
//        assertNotNull(result);
//        assertEquals(expectApproved, result.isApproved());
//        assertEquals(expectedMessage, result.getMessage());
//    }
//}
