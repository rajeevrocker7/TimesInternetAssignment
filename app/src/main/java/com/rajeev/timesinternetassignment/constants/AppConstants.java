package com.rajeev.timesinternetassignment.constants;


import java.io.Serializable;
import java.util.Objects;

/**
 * Created by Rajeev Kr. Sharma [rajeevrocker7@gmail.com] on 13/11/18.
 */

public interface AppConstants extends Serializable {


    String kAppPreferences = "TimesInternetAssignmentPrefrences";
    String kDefaultAppName = "TimesInternetAssignment";

    String kCurrentUser = "currentUser";
    String kData = "data";
    String kStatus = "status";
    String kMessage = "message";
    String kMsg = "msg";
    String kSUCCESS = "SUCCESS";
    String kFAILURE = "FAILURE";

    String kSeparator = "__";
    String kEmptyString = "";
    String kWhitespace = " ";
    Number kEmptyNumber = 0;

    String kMessageInternalInconsistency = "Some internal inconsistency occurred. Please try again.";
    String kMessageNetworkError = "Device does not connect to internet.";
    String kSocketTimeOut = kDefaultAppName + " Server not responding! Time Out.";
    String kMessageServerNotRespondingError = kDefaultAppName + " Server not responding!";
    String kMessageConnecting = "Connecting...";
    String kMessageSomeWentWrong = "Something went wrong!";
    String kError = "Error";
    String kDeclinedPayment = "Payment Declined due to some error.";
    String kApprovePlanPayment = "Plan Purchased successfully.";
    String kMakeSureInternet = "Make sure you are connected to Internet.";
    String kWeOnlineInternet = "We are back online.";


    /*******
     * SERVICES (API) NAME CONSTANTS:
     *****/
    String kProductsList = "product/";



    /******
     * SERVICES (API) PARAMETERS CONSTANTS:
     *****/


    String kLimit = "limit";
    String kFormat = "format";
    String kOffset = "offset";
    String kItemInfoId = "id";


    enum DeviceType {
        eiOS("ios"),
        eAndroid("android");

        private String value;

        DeviceType(String deviceType) {
            this.value = deviceType;
        }

        /**
         * Convert int to DeviceType Type
         */
        public static DeviceType getDeviceType(String value) {
            for (DeviceType deviceType : DeviceType.values()) {
                if (Objects.equals(deviceType.value, value)) {
                    return deviceType;
                }
            }
            return eAndroid;
        }

        /**
         * To get Integer value of corresponding enum
         */
        public String getValue() {
            return this.value;
        }
    }

}
