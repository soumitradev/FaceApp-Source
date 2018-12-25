package com.parrot.arsdk.arcontroller;

import com.parrot.arsdk.arcommands.ARCOMMANDS_ARDRONE3_ANIMATIONS_FLIP_DIRECTION_ENUM;
import com.parrot.arsdk.arcommands.ARCOMMANDS_ARDRONE3_ANTIFLICKERING_SETMODE_MODE_ENUM;
import com.parrot.arsdk.arcommands.ARCOMMANDS_ARDRONE3_GPSSETTINGS_HOMETYPE_TYPE_ENUM;
import com.parrot.arsdk.arcommands.ARCOMMANDS_ARDRONE3_MEDIARECORD_VIDEOV2_RECORD_ENUM;
import com.parrot.arsdk.arcommands.ARCOMMANDS_ARDRONE3_MEDIARECORD_VIDEO_RECORD_ENUM;
import com.parrot.arsdk.arcommands.ARCOMMANDS_ARDRONE3_MEDIASTREAMING_VIDEOSTREAMMODE_MODE_ENUM;
import com.parrot.arsdk.arcommands.ARCOMMANDS_ARDRONE3_NETWORKSETTINGS_WIFISECURITY_KEYTYPE_ENUM;
import com.parrot.arsdk.arcommands.ARCOMMANDS_ARDRONE3_NETWORKSETTINGS_WIFISECURITY_TYPE_ENUM;
import com.parrot.arsdk.arcommands.ARCOMMANDS_ARDRONE3_NETWORKSETTINGS_WIFISELECTION_BAND_ENUM;
import com.parrot.arsdk.arcommands.ARCOMMANDS_ARDRONE3_NETWORKSETTINGS_WIFISELECTION_TYPE_ENUM;
import com.parrot.arsdk.arcommands.ARCOMMANDS_ARDRONE3_NETWORK_WIFISCAN_BAND_ENUM;
import com.parrot.arsdk.arcommands.ARCOMMANDS_ARDRONE3_PICTURESETTINGS_VIDEORECORDINGMODE_MODE_ENUM;
import com.parrot.arsdk.arcommands.ARCOMMANDS_ARDRONE3_PICTURESETTINGS_VIDEORESOLUTIONS_TYPE_ENUM;
import com.parrot.arsdk.arcommands.ARCOMMANDS_ARDRONE3_PILOTINGSETTINGS_PITCHMODE_VALUE_ENUM;
import com.parrot.arsdk.arcommands.ARCOMMANDS_ARDRONE3_PILOTING_CIRCLE_DIRECTION_ENUM;
import com.parrot.arsdk.arcommands.ARCOMMANDS_ARDRONE3_PILOTING_MOVETO_ORIENTATION_MODE_ENUM;
import com.parrot.arsdk.arcommands.C1369x263150ea;
import com.parrot.arsdk.arcommands.C1396x10f9ec46;
import com.parrot.arsdk.arcommands.C1397x91f54a09;
import com.parrot.arsdk.arcommands.C1398xcc3fb1b5;
import com.parrot.arsdk.arcommands.C1399x62185770;
import com.parrot.arsdk.arcommands.C1402xbc6011c1;

public class ARFeatureARDrone3 {
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_ACCESSORYSTATE_CONNECTEDACCESSORIES_ACCESSORY_TYPE */
    public static String f1235x7de0aa5b;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_ACCESSORYSTATE_CONNECTEDACCESSORIES_ID */
    public static String f1236xcd22990f;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_ACCESSORYSTATE_CONNECTEDACCESSORIES_SWVERSION */
    public static String f1237xf2c43b60;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_ACCESSORYSTATE_CONNECTEDACCESSORIES_UID */
    public static String f1238xd730b6bc;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_ANTIFLICKERINGSTATE_ELECTRICFREQUENCYCHANGED_FREQUENCY */
    public static String f1239xb6705588;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_ANTIFLICKERINGSTATE_MODECHANGED_MODE */
    public static String f1240xc4ef3227;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_CAMERASTATE_DEFAULTCAMERAORIENTATIONV2_PAN */
    public static String f1241xe585ff33;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_CAMERASTATE_DEFAULTCAMERAORIENTATIONV2_TILT */
    public static String f1242xcb3bd6c7;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_CAMERASTATE_DEFAULTCAMERAORIENTATION_PAN */
    public static String f1243x768884d7;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_CAMERASTATE_DEFAULTCAMERAORIENTATION_TILT */
    public static String f1244x5a8a05a3;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_CAMERASTATE_ORIENTATIONV2_PAN */
    public static String f1245xbf154bb;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_CAMERASTATE_ORIENTATIONV2_TILT */
    public static String f1246x723b323f;
    public static String ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_CAMERASTATE_ORIENTATION_PAN;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_CAMERASTATE_ORIENTATION_TILT */
    public static String f1247xa619df1b;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_CAMERASTATE_VELOCITYRANGE_MAX_PAN */
    public static String f1248xf6d97654;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_CAMERASTATE_VELOCITYRANGE_MAX_TILT */
    public static String f1249xe45743c6;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_GPSSETTINGSSTATE_GEOFENCECENTERCHANGED_LATITUDE */
    public static String f1250x424e0c0b;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_GPSSETTINGSSTATE_GEOFENCECENTERCHANGED_LONGITUDE */
    public static String f1251x74599dd0;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_GPSSETTINGSSTATE_GPSFIXSTATECHANGED_FIXED */
    public static String f1252xa0f99742;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_GPSSETTINGSSTATE_GPSUPDATESTATECHANGED_STATE */
    public static String f1253x17608929;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_GPSSETTINGSSTATE_HOMECHANGED_ALTITUDE */
    public static String f1254x9ca2aa0b;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_GPSSETTINGSSTATE_HOMECHANGED_LATITUDE */
    public static String f1255xcd6b0bd5;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_GPSSETTINGSSTATE_HOMECHANGED_LONGITUDE */
    public static String f1256x4cdc9746;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_GPSSETTINGSSTATE_HOMETYPECHANGED_TYPE */
    public static String f1257x35abd45d;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_GPSSETTINGSSTATE_RESETHOMECHANGED_ALTITUDE */
    public static String f1258x8bc3a29c;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_GPSSETTINGSSTATE_RESETHOMECHANGED_LATITUDE */
    public static String f1259xbc8c0466;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_GPSSETTINGSSTATE_RESETHOMECHANGED_LONGITUDE */
    public static String f1260x41dab0d5;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_GPSSETTINGSSTATE_RETURNHOMEDELAYCHANGED_DELAY */
    public static String f1261xad6ef403;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_GPSSTATE_HOMETYPEAVAILABILITYCHANGED_AVAILABLE */
    public static String f1262x7cf296e;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_GPSSTATE_HOMETYPEAVAILABILITYCHANGED_TYPE */
    public static String f1263x2f6b1155;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_GPSSTATE_HOMETYPECHOSENCHANGED_TYPE */
    public static String f1264x384e5cac;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_GPSSTATE_NUMBEROFSATELLITECHANGED_NUMBEROFSATELLITE */
    public static String f1265xef8be551;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_MEDIARECORDEVENT_PICTUREEVENTCHANGED_ERROR */
    public static String f1266xe9490723;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_MEDIARECORDEVENT_PICTUREEVENTCHANGED_EVENT */
    public static String f1267xe94aa7b5;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_MEDIARECORDEVENT_VIDEOEVENTCHANGED_ERROR */
    public static String f1268x4c90ade0;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_MEDIARECORDEVENT_VIDEOEVENTCHANGED_EVENT */
    public static String f1269x4c924e72;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_MEDIARECORDSTATE_PICTURESTATECHANGEDV2_ERROR */
    public static String f1270xfc6ef0df;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_MEDIARECORDSTATE_PICTURESTATECHANGEDV2_STATE */
    public static String f1271xfd34e368;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_MEDIARECORDSTATE_PICTURESTATECHANGED_MASS_STORAGE_ID */
    public static String f1272xfdbaddc5;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_MEDIARECORDSTATE_PICTURESTATECHANGED_STATE */
    public static String f1273x28dc0c4c;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_MEDIARECORDSTATE_VIDEORESOLUTIONSTATE_RECORDING */
    public static String f1274x5c49843;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_MEDIARECORDSTATE_VIDEORESOLUTIONSTATE_STREAMING */
    public static String f1275xb7bc32b4;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_MEDIARECORDSTATE_VIDEOSTATECHANGEDV2_ERROR */
    public static String f1276x5d89ce1c;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_MEDIARECORDSTATE_VIDEOSTATECHANGEDV2_STATE */
    public static String f1277x5e4fc0a5;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_MEDIARECORDSTATE_VIDEOSTATECHANGED_MASS_STORAGE_ID */
    public static String f1278xcf72e02;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_MEDIARECORDSTATE_VIDEOSTATECHANGED_STATE */
    public static String f1279x8c9714c9;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_MEDIASTREAMINGSTATE_VIDEOENABLECHANGED_ENABLED */
    public static String f1280xe09a2160;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_MEDIASTREAMINGSTATE_VIDEOSTREAMMODECHANGED_MODE */
    public static String f1281x23eb31e4;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_NETWORKSETTINGSSTATE_WIFISECURITYCHANGED_TYPE */
    public static String f1282x95562c1d;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_NETWORKSETTINGSSTATE_WIFISECURITY_KEY */
    public static String f1283x45b0c798;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_NETWORKSETTINGSSTATE_WIFISECURITY_KEYTYPE */
    public static String f1284xea7ac2d2;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_NETWORKSETTINGSSTATE_WIFISECURITY_TYPE */
    public static String f1285x706c8d01;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_NETWORKSETTINGSSTATE_WIFISELECTIONCHANGED_BAND */
    public static String f1286xf83c0894;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_NETWORKSETTINGSSTATE_WIFISELECTIONCHANGED_CHANNEL */
    public static String f1287x9250ee24;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_NETWORKSETTINGSSTATE_WIFISELECTIONCHANGED_TYPE */
    public static String f1288xf8449199;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_NETWORKSTATE_WIFIAUTHCHANNELLISTCHANGED_BAND */
    public static String f1289x71387744;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_NETWORKSTATE_WIFIAUTHCHANNELLISTCHANGED_CHANNEL */
    public static String f1290xda25c174;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_NETWORKSTATE_WIFIAUTHCHANNELLISTCHANGED_IN_OR_OUT */
    public static String f1291x590eb7d;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_NETWORKSTATE_WIFISCANLISTCHANGED_BAND */
    public static String f1292x2e6e5110;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_NETWORKSTATE_WIFISCANLISTCHANGED_CHANNEL */
    public static String f1293x77d20428;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_NETWORKSTATE_WIFISCANLISTCHANGED_RSSI */
    public static String f1294x2e75db32;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_NETWORKSTATE_WIFISCANLISTCHANGED_SSID */
    public static String f1295x2e764e56;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_PICTURESETTINGSSTATE_AUTOWHITEBALANCECHANGED_TYPE */
    public static String f1296xc4ea6e5a;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_PICTURESETTINGSSTATE_EXPOSITIONCHANGED_MAX */
    public static String f1297xa928aa8a;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_PICTURESETTINGSSTATE_EXPOSITIONCHANGED_MIN */
    public static String f1298xa928ab78;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_PICTURESETTINGSSTATE_EXPOSITIONCHANGED_VALUE */
    public static String f1299x226e117;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_PICTURESETTINGSSTATE_PICTUREFORMATCHANGED_TYPE */
    public static String f1300x4c479c27;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_PICTURESETTINGSSTATE_SATURATIONCHANGED_MAX */
    public static String f1301xfed7dbd4;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_PICTURESETTINGSSTATE_SATURATIONCHANGED_MIN */
    public static String f1302xfed7dcc2;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_PICTURESETTINGSSTATE_SATURATIONCHANGED_VALUE */
    public static String f1303xa8cee7e1;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_PICTURESETTINGSSTATE_TIMELAPSECHANGED_ENABLED */
    public static String f1304x82341529;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_PICTURESETTINGSSTATE_TIMELAPSECHANGED_INTERVAL */
    public static String f1305x846c97bd;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_PICTURESETTINGSSTATE_TIMELAPSECHANGED_MAXINTERVAL */
    public static String f1306x5eb63411;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_PICTURESETTINGSSTATE_TIMELAPSECHANGED_MININTERVAL */
    public static String f1307x365566ff;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_PICTURESETTINGSSTATE_VIDEOAUTORECORDCHANGED_ENABLED */
    public static String f1308xd73128e;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_PICTURESETTINGSSTATE_VIDEOAUTORECORDCHANGED_MASS_STORAGE_ID */
    public static String f1309xb745b217;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_PICTURESETTINGSSTATE_VIDEOFRAMERATECHANGED_FRAMERATE */
    public static String f1310xc2c901dd;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_PICTURESETTINGSSTATE_VIDEORECORDINGMODECHANGED_MODE */
    public static String f1311x9a09223a;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_PICTURESETTINGSSTATE_VIDEORESOLUTIONSCHANGED_TYPE */
    public static String f1312x89c9db84;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_PICTURESETTINGSSTATE_VIDEOSTABILIZATIONMODECHANGED_MODE */
    public static String f1313x6f00228;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_PILOTINGEVENT_MOVEBYEND_DPSI */
    public static String f1314xffe37e19;
    public static String ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_PILOTINGEVENT_MOVEBYEND_DX;
    public static String ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_PILOTINGEVENT_MOVEBYEND_DY;
    public static String ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_PILOTINGEVENT_MOVEBYEND_DZ;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_PILOTINGEVENT_MOVEBYEND_ERROR */
    public static String f1315xfc9b4291;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_PILOTINGSETTINGSSTATE_ABSOLUTCONTROLCHANGED_ON */
    public static String f1316x7a02e1f0;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_PILOTINGSETTINGSSTATE_AUTONOMOUSFLIGHTMAXHORIZONTALACCELERATION_VALUE */
    public static String f1317xeb8b9523;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_PILOTINGSETTINGSSTATE_AUTONOMOUSFLIGHTMAXHORIZONTALSPEED_VALUE */
    public static String f1318x3fe51808;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_PILOTINGSETTINGSSTATE_AUTONOMOUSFLIGHTMAXROTATIONSPEED_VALUE */
    public static String f1319x8cdf952e;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_PILOTINGSETTINGSSTATE_AUTONOMOUSFLIGHTMAXVERTICALACCELERATION_VALUE */
    public static String f1320x5d516b35;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_PILOTINGSETTINGSSTATE_AUTONOMOUSFLIGHTMAXVERTICALSPEED_VALUE */
    public static String f1321xf0281236;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_PILOTINGSETTINGSSTATE_BANKEDTURNCHANGED_STATE */
    public static String f1322x840ca3f7;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_PILOTINGSETTINGSSTATE_CIRCLINGALTITUDECHANGED_CURRENT */
    public static String f1323xbb453b68;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_PILOTINGSETTINGSSTATE_CIRCLINGALTITUDECHANGED_MAX */
    public static String f1324xa7807ad3;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_PILOTINGSETTINGSSTATE_CIRCLINGALTITUDECHANGED_MIN */
    public static String f1325xa7807bc1;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_PILOTINGSETTINGSSTATE_CIRCLINGDIRECTIONCHANGED_VALUE */
    public static String f1326xd0709fcb;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_PILOTINGSETTINGSSTATE_CIRCLINGRADIUSCHANGED_CURRENT */
    public static String f1327x6c72ae78;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_PILOTINGSETTINGSSTATE_CIRCLINGRADIUSCHANGED_MAX */
    public static String f1328x3b01f5e3;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_PILOTINGSETTINGSSTATE_CIRCLINGRADIUSCHANGED_MIN */
    public static String f1329x3b01f6d1;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_PILOTINGSETTINGSSTATE_MAXALTITUDECHANGED_CURRENT */
    public static String f1330xde69209f;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_PILOTINGSETTINGSSTATE_MAXALTITUDECHANGED_MAX */
    public static String f1331x64dd938a;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_PILOTINGSETTINGSSTATE_MAXALTITUDECHANGED_MIN */
    public static String f1332x64dd9478;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_PILOTINGSETTINGSSTATE_MAXDISTANCECHANGED_CURRENT */
    public static String f1333x1c7baeec;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_PILOTINGSETTINGSSTATE_MAXDISTANCECHANGED_MAX */
    public static String f1334x19e55057;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_PILOTINGSETTINGSSTATE_MAXDISTANCECHANGED_MIN */
    public static String f1335x19e55145;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_PILOTINGSETTINGSSTATE_MAXTILTCHANGED_CURRENT */
    public static String f1336xf28ed8a4;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_PILOTINGSETTINGSSTATE_MAXTILTCHANGED_MAX */
    public static String f1337x5bc2160f;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_PILOTINGSETTINGSSTATE_MAXTILTCHANGED_MIN */
    public static String f1338x5bc216fd;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_PILOTINGSETTINGSSTATE_MINALTITUDECHANGED_CURRENT */
    public static String f1339x27dbf3f1;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_PILOTINGSETTINGSSTATE_MINALTITUDECHANGED_MAX */
    public static String f1340xbd52dfdc;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_PILOTINGSETTINGSSTATE_MINALTITUDECHANGED_MIN */
    public static String f1341xbd52e0ca;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_PILOTINGSETTINGSSTATE_NOFLYOVERMAXDISTANCECHANGED_SHOULDNOTFLYOVER */
    public static String f1342x3934425c;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_PILOTINGSETTINGSSTATE_PITCHMODECHANGED_VALUE */
    public static String f1343xbabbb61a;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_PILOTINGSTATE_AIRSPEEDCHANGED_AIRSPEED */
    public static String f1344x4eadab39;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_PILOTINGSTATE_ALERTSTATECHANGED_STATE */
    public static String f1345x987bdc7d;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_PILOTINGSTATE_ALTITUDECHANGED_ALTITUDE */
    public static String f1346x632b4c03;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_PILOTINGSTATE_ATTITUDECHANGED_PITCH */
    public static String f1347x9c39d057;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_PILOTINGSTATE_ATTITUDECHANGED_ROLL */
    public static String f1348x2e557066;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_PILOTINGSTATE_ATTITUDECHANGED_YAW */
    public static String f1349x438f3d46;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_PILOTINGSTATE_AUTOTAKEOFFMODECHANGED_STATE */
    public static String f1350xcb8a97fe;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_PILOTINGSTATE_FLYINGSTATECHANGED_STATE */
    public static String f1351xa0666478;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_PILOTINGSTATE_GPSLOCATIONCHANGED_ALTITUDE */
    public static String f1352xebbcea38;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_PILOTINGSTATE_GPSLOCATIONCHANGED_ALTITUDE_ACCURACY */
    public static String f1353xce36ef20;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_PILOTINGSTATE_GPSLOCATIONCHANGED_LATITUDE */
    public static String f1354x1c854c02;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_PILOTINGSTATE_GPSLOCATIONCHANGED_LATITUDE_ACCURACY */
    public static String f1355x943ef096;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_PILOTINGSTATE_GPSLOCATIONCHANGED_LONGITUDE */
    public static String f1356xe10a5cb9;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_PILOTINGSTATE_GPSLOCATIONCHANGED_LONGITUDE_ACCURACY */
    public static String f1357x4cebbdbf;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_PILOTINGSTATE_LANDINGSTATECHANGED_STATE */
    public static String f1358x9140a578;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_PILOTINGSTATE_MOVETOCHANGED_ALTITUDE */
    public static String f1359xb48e586d;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_PILOTINGSTATE_MOVETOCHANGED_HEADING */
    public static String f1360xe6fd86b7;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_PILOTINGSTATE_MOVETOCHANGED_LATITUDE */
    public static String f1361xe556ba37;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_PILOTINGSTATE_MOVETOCHANGED_LONGITUDE */
    public static String f1362x3266b524;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_PILOTINGSTATE_MOVETOCHANGED_ORIENTATION_MODE */
    public static String f1363x7d37119d;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_PILOTINGSTATE_MOVETOCHANGED_STATUS */
    public static String f1364x4c98d87d;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_PILOTINGSTATE_NAVIGATEHOMESTATECHANGED_REASON */
    public static String f1365xefb093c;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_PILOTINGSTATE_NAVIGATEHOMESTATECHANGED_STATE */
    public static String f1366xb63e0a79;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_PILOTINGSTATE_POSITIONCHANGED_ALTITUDE */
    public static String f1367x2392ac8a;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_PILOTINGSTATE_POSITIONCHANGED_LATITUDE */
    public static String f1368x545b0e54;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_PILOTINGSTATE_POSITIONCHANGED_LONGITUDE */
    public static String f1369xa3ece4a7;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_PILOTINGSTATE_SPEEDCHANGED_SPEEDX */
    public static String f1370x44e46b0f;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_PILOTINGSTATE_SPEEDCHANGED_SPEEDY */
    public static String f1371x44e46b10;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_PILOTINGSTATE_SPEEDCHANGED_SPEEDZ */
    public static String f1372x44e46b11;
    public static String ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_PROSTATE_FEATURES_FEATURES;
    public static String ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_SETTINGSSTATE_CPUID_ID;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_SETTINGSSTATE_MOTORERRORLASTERRORCHANGED_MOTORERROR */
    public static String f1373xfc134ab0;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_SETTINGSSTATE_MOTORERRORSTATECHANGED_MOTORERROR */
    public static String f1374xe179904f;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_SETTINGSSTATE_MOTORERRORSTATECHANGED_MOTORIDS */
    public static String f1375x10c03ebf;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_SETTINGSSTATE_MOTORFLIGHTSSTATUSCHANGED_LASTFLIGHTDURATION */
    public static String f1376xcedb3c32;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_SETTINGSSTATE_MOTORFLIGHTSSTATUSCHANGED_NBFLIGHTS */
    public static String f1377xbd841497;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_SETTINGSSTATE_MOTORFLIGHTSSTATUSCHANGED_TOTALFLIGHTDURATION */
    public static String f1378x88397310;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_SETTINGSSTATE_MOTORSOFTWAREVERSIONCHANGED_VERSION */
    public static String f1379x158c4684;
    public static String ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_SETTINGSSTATE_P7ID_SERIALID;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_SETTINGSSTATE_PRODUCTGPSVERSIONCHANGED_HARDWARE */
    public static String f1380xdae90123;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_SETTINGSSTATE_PRODUCTGPSVERSIONCHANGED_SOFTWARE */
    public static String f1381x22947ce2;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_SETTINGSSTATE_PRODUCTMOTORVERSIONLISTCHANGED_HARDWARE */
    public static String f1382x302c0a96;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_SETTINGSSTATE_PRODUCTMOTORVERSIONLISTCHANGED_MOTOR_NUMBER */
    public static String f1383x879da021;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_SETTINGSSTATE_PRODUCTMOTORVERSIONLISTCHANGED_SOFTWARE */
    public static String f1384x77d78655;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_SETTINGSSTATE_PRODUCTMOTORVERSIONLISTCHANGED_TYPE */
    public static String f1385xe57742e8;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_SPEEDSETTINGSSTATE_HULLPROTECTIONCHANGED_PRESENT */
    public static String f1386x535dfc2e;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_SPEEDSETTINGSSTATE_MAXPITCHROLLROTATIONSPEEDCHANGED_CURRENT */
    public static String f1387x219c81a;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_SPEEDSETTINGSSTATE_MAXPITCHROLLROTATIONSPEEDCHANGED_MAX */
    public static String f1388xb2eb3085;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_SPEEDSETTINGSSTATE_MAXPITCHROLLROTATIONSPEEDCHANGED_MIN */
    public static String f1389xb2eb3173;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_SPEEDSETTINGSSTATE_MAXROTATIONSPEEDCHANGED_CURRENT */
    public static String f1390x9e17634d;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_SPEEDSETTINGSSTATE_MAXROTATIONSPEEDCHANGED_MAX */
    public static String f1391xcd845d38;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_SPEEDSETTINGSSTATE_MAXROTATIONSPEEDCHANGED_MIN */
    public static String f1392xcd845e26;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_SPEEDSETTINGSSTATE_MAXVERTICALSPEEDCHANGED_CURRENT */
    public static String f1393xea570f45;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_SPEEDSETTINGSSTATE_MAXVERTICALSPEEDCHANGED_MAX */
    public static String f1394x92a8c530;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_SPEEDSETTINGSSTATE_MAXVERTICALSPEEDCHANGED_MIN */
    public static String f1395x92a8c61e;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_SPEEDSETTINGSSTATE_OUTDOORCHANGED_OUTDOOR */
    public static String f1396xc37bf971;
    private static String TAG = "ARFeatureARDrone3";
    private boolean initOk = false;
    private long jniFeature;

    private native int nativeSendAnimationsFlip(long j, int i);

    private native int nativeSendAntiflickeringElectricFrequency(long j, int i);

    private native int nativeSendAntiflickeringSetMode(long j, int i);

    private native int nativeSendCameraOrientation(long j, byte b, byte b2);

    private native int nativeSendCameraOrientationV2(long j, float f, float f2);

    private native int nativeSendCameraVelocity(long j, float f, float f2);

    private native int nativeSendGPSSettingsHomeType(long j, int i);

    private native int nativeSendGPSSettingsResetHome(long j);

    private native int nativeSendGPSSettingsReturnHomeDelay(long j, short s);

    private native int nativeSendGPSSettingsSendControllerGPS(long j, double d, double d2, double d3, double d4, double d5);

    private native int nativeSendGPSSettingsSetHome(long j, double d, double d2, double d3);

    private native int nativeSendMediaRecordPicture(long j, byte b);

    private native int nativeSendMediaRecordPictureV2(long j);

    private native int nativeSendMediaRecordVideo(long j, int i, byte b);

    private native int nativeSendMediaRecordVideoV2(long j, int i);

    private native int nativeSendMediaStreamingVideoEnable(long j, byte b);

    private native int nativeSendMediaStreamingVideoStreamMode(long j, int i);

    private native int nativeSendNetworkSettingsWifiSecurity(long j, int i, String str, int i2);

    private native int nativeSendNetworkSettingsWifiSelection(long j, int i, int i2, byte b);

    private native int nativeSendNetworkWifiAuthChannel(long j);

    private native int nativeSendNetworkWifiScan(long j, int i);

    private native int nativeSendPictureSettingsAutoWhiteBalanceSelection(long j, int i);

    private native int nativeSendPictureSettingsExpositionSelection(long j, float f);

    private native int nativeSendPictureSettingsPictureFormatSelection(long j, int i);

    private native int nativeSendPictureSettingsSaturationSelection(long j, float f);

    private native int nativeSendPictureSettingsTimelapseSelection(long j, byte b, float f);

    private native int nativeSendPictureSettingsVideoAutorecordSelection(long j, byte b, byte b2);

    private native int nativeSendPictureSettingsVideoFramerate(long j, int i);

    private native int nativeSendPictureSettingsVideoRecordingMode(long j, int i);

    private native int nativeSendPictureSettingsVideoResolutions(long j, int i);

    private native int nativeSendPictureSettingsVideoStabilizationMode(long j, int i);

    private native int nativeSendPilotingAutoTakeOffMode(long j, byte b);

    private native int nativeSendPilotingCancelMoveTo(long j);

    private native int nativeSendPilotingCircle(long j, int i);

    private native int nativeSendPilotingEmergency(long j);

    private native int nativeSendPilotingFlatTrim(long j);

    private native int nativeSendPilotingLanding(long j);

    private native int nativeSendPilotingMoveBy(long j, float f, float f2, float f3, float f4);

    private native int nativeSendPilotingMoveTo(long j, double d, double d2, double d3, int i, float f);

    private native int nativeSendPilotingNavigateHome(long j, byte b);

    private native int nativeSendPilotingPCMD(long j, byte b, byte b2, byte b3, byte b4, byte b5, int i);

    private native int nativeSendPilotingSettingsAbsolutControl(long j, byte b);

    private native int nativeSendPilotingSettingsBankedTurn(long j, byte b);

    private native int nativeSendPilotingSettingsCirclingAltitude(long j, short s);

    private native int nativeSendPilotingSettingsCirclingDirection(long j, int i);

    private native int nativeSendPilotingSettingsCirclingRadius(long j, short s);

    private native int nativeSendPilotingSettingsMaxAltitude(long j, float f);

    private native int nativeSendPilotingSettingsMaxDistance(long j, float f);

    private native int nativeSendPilotingSettingsMaxTilt(long j, float f);

    private native int nativeSendPilotingSettingsMinAltitude(long j, float f);

    private native int nativeSendPilotingSettingsNoFlyOverMaxDistance(long j, byte b);

    private native int nativeSendPilotingSettingsPitchMode(long j, int i);

    /* renamed from: nativeSendPilotingSettingsSetAutonomousFlightMaxHorizontalAcceleration */
    private native int m211x18ed7fd2(long j, float f);

    private native int nativeSendPilotingSettingsSetAutonomousFlightMaxHorizontalSpeed(long j, float f);

    private native int nativeSendPilotingSettingsSetAutonomousFlightMaxRotationSpeed(long j, float f);

    /* renamed from: nativeSendPilotingSettingsSetAutonomousFlightMaxVerticalAcceleration */
    private native int m212xae9cdba4(long j, float f);

    private native int nativeSendPilotingSettingsSetAutonomousFlightMaxVerticalSpeed(long j, float f);

    private native int nativeSendPilotingTakeOff(long j);

    private native int nativeSendPilotingUserTakeOff(long j, byte b);

    private native int nativeSendSpeedSettingsHullProtection(long j, byte b);

    private native int nativeSendSpeedSettingsMaxPitchRollRotationSpeed(long j, float f);

    private native int nativeSendSpeedSettingsMaxRotationSpeed(long j, float f);

    private native int nativeSendSpeedSettingsMaxVerticalSpeed(long j, float f);

    private native int nativeSendSpeedSettingsOutdoor(long j, byte b);

    private native int nativeSetCameraOrientation(long j, byte b, byte b2);

    private native int nativeSetCameraOrientationPan(long j, byte b);

    private native int nativeSetCameraOrientationTilt(long j, byte b);

    private native int nativeSetCameraOrientationV2(long j, float f, float f2);

    private native int nativeSetCameraOrientationV2Pan(long j, float f);

    private native int nativeSetCameraOrientationV2Tilt(long j, float f);

    private native int nativeSetCameraVelocity(long j, float f, float f2);

    private native int nativeSetCameraVelocityPan(long j, float f);

    private native int nativeSetCameraVelocityTilt(long j, float f);

    private native int nativeSetPilotingPCMD(long j, byte b, byte b2, byte b3, byte b4, byte b5, int i);

    private native int nativeSetPilotingPCMDFlag(long j, byte b);

    private native int nativeSetPilotingPCMDGaz(long j, byte b);

    private native int nativeSetPilotingPCMDPitch(long j, byte b);

    private native int nativeSetPilotingPCMDRoll(long j, byte b);

    private native int nativeSetPilotingPCMDTimestampAndSeqNum(long j, int i);

    private native int nativeSetPilotingPCMDYaw(long j, byte b);

    /* renamed from: nativeStaticGetKeyARDrone3AccessoryStateConnectedAccessoriesAccessorytype */
    private static native String m213xc821a94c();

    private static native String nativeStaticGetKeyARDrone3AccessoryStateConnectedAccessoriesId();

    /* renamed from: nativeStaticGetKeyARDrone3AccessoryStateConnectedAccessoriesListflags */
    private static native String m214x9b1894a9();

    /* renamed from: nativeStaticGetKeyARDrone3AccessoryStateConnectedAccessoriesSwVersion */
    private static native String m215x404711d4();

    private static native String nativeStaticGetKeyARDrone3AccessoryStateConnectedAccessoriesUid();

    /* renamed from: nativeStaticGetKeyARDrone3AntiflickeringStateElectricFrequencyChangedFrequency */
    private static native String m216x2ebf21fc();

    private static native String nativeStaticGetKeyARDrone3AntiflickeringStateModeChangedMode();

    private static native String nativeStaticGetKeyARDrone3CameraStateDefaultCameraOrientationPan();

    /* renamed from: nativeStaticGetKeyARDrone3CameraStateDefaultCameraOrientationTilt */
    private static native String m217xe3ae2599();

    /* renamed from: nativeStaticGetKeyARDrone3CameraStateDefaultCameraOrientationV2Pan */
    private static native String m218x92195125();

    /* renamed from: nativeStaticGetKeyARDrone3CameraStateDefaultCameraOrientationV2Tilt */
    private static native String m219xb112c335();

    private static native String nativeStaticGetKeyARDrone3CameraStateOrientationPan();

    private static native String nativeStaticGetKeyARDrone3CameraStateOrientationTilt();

    private static native String nativeStaticGetKeyARDrone3CameraStateOrientationV2Pan();

    private static native String nativeStaticGetKeyARDrone3CameraStateOrientationV2Tilt();

    private static native String nativeStaticGetKeyARDrone3CameraStateVelocityRangeMaxpan();

    private static native String nativeStaticGetKeyARDrone3CameraStateVelocityRangeMaxtilt();

    /* renamed from: nativeStaticGetKeyARDrone3GPSSettingsStateGPSFixStateChangedFixed */
    private static native String m220xfc9fca48();

    /* renamed from: nativeStaticGetKeyARDrone3GPSSettingsStateGPSUpdateStateChangedState */
    private static native String m221x496dc299();

    /* renamed from: nativeStaticGetKeyARDrone3GPSSettingsStateGeofenceCenterChangedLatitude */
    private static native String m222x75d946cd();

    /* renamed from: nativeStaticGetKeyARDrone3GPSSettingsStateGeofenceCenterChangedLongitude */
    private static native String m223xb235bb6e();

    private static native String nativeStaticGetKeyARDrone3GPSSettingsStateHomeChangedAltitude();

    private static native String nativeStaticGetKeyARDrone3GPSSettingsStateHomeChangedLatitude();

    private static native String nativeStaticGetKeyARDrone3GPSSettingsStateHomeChangedLongitude();

    private static native String nativeStaticGetKeyARDrone3GPSSettingsStateHomeTypeChangedType();

    /* renamed from: nativeStaticGetKeyARDrone3GPSSettingsStateResetHomeChangedAltitude */
    private static native String m224x266fbb46();

    /* renamed from: nativeStaticGetKeyARDrone3GPSSettingsStateResetHomeChangedLatitude */
    private static native String m225x57381d10();

    /* renamed from: nativeStaticGetKeyARDrone3GPSSettingsStateResetHomeChangedLongitude */
    private static native String m226xfcb1ad8b();

    /* renamed from: nativeStaticGetKeyARDrone3GPSSettingsStateReturnHomeDelayChangedDelay */
    private static native String m227x7a9d72a5();

    /* renamed from: nativeStaticGetKeyARDrone3GPSStateHomeTypeAvailabilityChangedAvailable */
    private static native String m228x28b419aa();

    /* renamed from: nativeStaticGetKeyARDrone3GPSStateHomeTypeAvailabilityChangedType */
    private static native String m229x31ec77b9();

    private static native String nativeStaticGetKeyARDrone3GPSStateHomeTypeChosenChangedType();

    /* renamed from: nativeStaticGetKeyARDrone3GPSStateNumberOfSatelliteChangedNumberOfSatellite */
    private static native String m230x95c33941();

    /* renamed from: nativeStaticGetKeyARDrone3MediaRecordEventPictureEventChangedError */
    private static native String m231x5aa8522f();

    /* renamed from: nativeStaticGetKeyARDrone3MediaRecordEventPictureEventChangedEvent */
    private static native String m232x5aa9f2c1();

    private static native String nativeStaticGetKeyARDrone3MediaRecordEventVideoEventChangedError();

    private static native String nativeStaticGetKeyARDrone3MediaRecordEventVideoEventChangedEvent();

    /* renamed from: nativeStaticGetKeyARDrone3MediaRecordStatePictureStateChangedMassstorageid */
    private static native String m233x2ebaf7f7();

    /* renamed from: nativeStaticGetKeyARDrone3MediaRecordStatePictureStateChangedState */
    private static native String m234xb367f926();

    /* renamed from: nativeStaticGetKeyARDrone3MediaRecordStatePictureStateChangedV2Error */
    private static native String m235xa5d5e441();

    /* renamed from: nativeStaticGetKeyARDrone3MediaRecordStatePictureStateChangedV2State */
    private static native String m236xa69bd6ca();

    /* renamed from: nativeStaticGetKeyARDrone3MediaRecordStateVideoResolutionStateRecording */
    private static native String m237xae7e1871();

    /* renamed from: nativeStaticGetKeyARDrone3MediaRecordStateVideoResolutionStateStreaming */
    private static native String m238x6075b2e2();

    /* renamed from: nativeStaticGetKeyARDrone3MediaRecordStateVideoStateChangedMassstorageid */
    private static native String m239x9b54849a();

    private static native String nativeStaticGetKeyARDrone3MediaRecordStateVideoStateChangedState();

    /* renamed from: nativeStaticGetKeyARDrone3MediaRecordStateVideoStateChangedV2Error */
    private static native String m240x35326724();

    /* renamed from: nativeStaticGetKeyARDrone3MediaRecordStateVideoStateChangedV2State */
    private static native String m241x35f859ad();

    /* renamed from: nativeStaticGetKeyARDrone3MediaStreamingStateVideoEnableChangedEnabled */
    private static native String m242x24496812();

    /* renamed from: nativeStaticGetKeyARDrone3MediaStreamingStateVideoStreamModeChangedMode */
    private static native String m243xabcd97b2();

    /* renamed from: nativeStaticGetKeyARDrone3NetworkSettingsStateWifiSecurityChangedType */
    private static native String m244xb0fda19f();

    private static native String nativeStaticGetKeyARDrone3NetworkSettingsStateWifiSecurityKey();

    /* renamed from: nativeStaticGetKeyARDrone3NetworkSettingsStateWifiSecurityKeyType */
    private static native String m245xb55b804a();

    private static native String nativeStaticGetKeyARDrone3NetworkSettingsStateWifiSecurityType();

    /* renamed from: nativeStaticGetKeyARDrone3NetworkSettingsStateWifiSelectionChangedBand */
    private static native String m246x1c387b0c();

    /* renamed from: nativeStaticGetKeyARDrone3NetworkSettingsStateWifiSelectionChangedChannel */
    private static native String m247x50de44cc();

    /* renamed from: nativeStaticGetKeyARDrone3NetworkSettingsStateWifiSelectionChangedType */
    private static native String m248x1c410411();

    /* renamed from: nativeStaticGetKeyARDrone3NetworkStateWifiAuthChannelListChangedBand */
    private static native String m249x523698e2();

    /* renamed from: nativeStaticGetKeyARDrone3NetworkStateWifiAuthChannelListChangedChannel */
    private static native String m250x7fb04f36();

    /* renamed from: nativeStaticGetKeyARDrone3NetworkStateWifiAuthChannelListChangedInorout */
    private static native String m251xc819e6b9();

    private static native String nativeStaticGetKeyARDrone3NetworkStateWifiScanListChangedBand();

    private static native String nativeStaticGetKeyARDrone3NetworkStateWifiScanListChangedChannel();

    private static native String nativeStaticGetKeyARDrone3NetworkStateWifiScanListChangedRssi();

    private static native String nativeStaticGetKeyARDrone3NetworkStateWifiScanListChangedSsid();

    private static native String nativeStaticGetKeyARDrone3PROStateFeaturesFeatures();

    /* renamed from: nativeStaticGetKeyARDrone3PictureSettingsStateAutoWhiteBalanceChangedType */
    private static native String m252x1b145702();

    /* renamed from: nativeStaticGetKeyARDrone3PictureSettingsStateExpositionChangedMax */
    private static native String m253x48e08b6();

    /* renamed from: nativeStaticGetKeyARDrone3PictureSettingsStateExpositionChangedMin */
    private static native String m254x48e09a4();

    /* renamed from: nativeStaticGetKeyARDrone3PictureSettingsStateExpositionChangedValue */
    private static native String m255x19ad6843();

    /* renamed from: nativeStaticGetKeyARDrone3PictureSettingsStatePictureFormatChangedType */
    private static native String m256x836d93e3();

    /* renamed from: nativeStaticGetKeyARDrone3PictureSettingsStateSaturationChangedMax */
    private static native String m257x7aee862c();

    /* renamed from: nativeStaticGetKeyARDrone3PictureSettingsStateSaturationChangedMin */
    private static native String m258x7aee871a();

    /* renamed from: nativeStaticGetKeyARDrone3PictureSettingsStateSaturationChangedValue */
    private static native String m259x79e46039();

    /* renamed from: nativeStaticGetKeyARDrone3PictureSettingsStateTimelapseChangedEnabled */
    private static native String m260xc48ada3();

    /* renamed from: nativeStaticGetKeyARDrone3PictureSettingsStateTimelapseChangedInterval */
    private static native String m261x3ceb0ea3();

    /* renamed from: nativeStaticGetKeyARDrone3PictureSettingsStateTimelapseChangedMaxInterval */
    private static native String m262x275f6bab();

    /* renamed from: nativeStaticGetKeyARDrone3PictureSettingsStateTimelapseChangedMinInterval */
    private static native String m263xfefe9e99();

    /* renamed from: nativeStaticGetKeyARDrone3PictureSettingsStateVideoAutorecordChangedEnabled */
    private static native String m264x6f67bcfe();

    /* renamed from: nativeStaticGetKeyARDrone3PictureSettingsStateVideoAutorecordChangedMassstorageid */
    private static native String m265x8926585f();

    /* renamed from: nativeStaticGetKeyARDrone3PictureSettingsStateVideoFramerateChangedFramerate */
    private static native String m266x9e31df55();

    /* renamed from: nativeStaticGetKeyARDrone3PictureSettingsStateVideoRecordingModeChangedMode */
    private static native String m267x306ed2d4();

    /* renamed from: nativeStaticGetKeyARDrone3PictureSettingsStateVideoResolutionsChangedType */
    private static native String m268xe715b4b8();

    /* renamed from: nativeStaticGetKeyARDrone3PictureSettingsStateVideoStabilizationModeChangedMode */
    private static native String m269x591518a6();

    private static native String nativeStaticGetKeyARDrone3PilotingEventMoveByEndDPsi();

    private static native String nativeStaticGetKeyARDrone3PilotingEventMoveByEndDX();

    private static native String nativeStaticGetKeyARDrone3PilotingEventMoveByEndDY();

    private static native String nativeStaticGetKeyARDrone3PilotingEventMoveByEndDZ();

    private static native String nativeStaticGetKeyARDrone3PilotingEventMoveByEndError();

    /* renamed from: nativeStaticGetKeyARDrone3PilotingSettingsStateAbsolutControlChangedOn */
    private static native String m270xcdebfe9a();

    /* renamed from: nativeStaticGetKeyARDrone3PilotingSettingsStateAutonomousFlightMaxHorizontalAccelerationValue */
    private static native String m271x3dec2973();

    /* renamed from: nativeStaticGetKeyARDrone3PilotingSettingsStateAutonomousFlightMaxHorizontalSpeedValue */
    private static native String m272x4d2fbb08();

    /* renamed from: nativeStaticGetKeyARDrone3PilotingSettingsStateAutonomousFlightMaxRotationSpeedValue */
    private static native String m273xf8212462();

    /* renamed from: nativeStaticGetKeyARDrone3PilotingSettingsStateAutonomousFlightMaxVerticalAccelerationValue */
    private static native String m274x51d4fde1();

    /* renamed from: nativeStaticGetKeyARDrone3PilotingSettingsStateAutonomousFlightMaxVerticalSpeedValue */
    private static native String m275xead0e65a();

    /* renamed from: nativeStaticGetKeyARDrone3PilotingSettingsStateBankedTurnChangedState */
    private static native String m276x1007a39f();

    /* renamed from: nativeStaticGetKeyARDrone3PilotingSettingsStateCirclingAltitudeChangedCurrent */
    private static native String m277xe644619e();

    /* renamed from: nativeStaticGetKeyARDrone3PilotingSettingsStateCirclingAltitudeChangedMax */
    private static native String m278xb3742409();

    /* renamed from: nativeStaticGetKeyARDrone3PilotingSettingsStateCirclingAltitudeChangedMin */
    private static native String m279xb37424f7();

    /* renamed from: nativeStaticGetKeyARDrone3PilotingSettingsStateCirclingDirectionChangedValue */
    private static native String m280x27ce7325();

    /* renamed from: nativeStaticGetKeyARDrone3PilotingSettingsStateCirclingRadiusChangedCurrent */
    private static native String m281x8d71814e();

    /* renamed from: nativeStaticGetKeyARDrone3PilotingSettingsStateCirclingRadiusChangedMax */
    private static native String m282x8c049bb9();

    /* renamed from: nativeStaticGetKeyARDrone3PilotingSettingsStateCirclingRadiusChangedMin */
    private static native String m283x8c049ca7();

    /* renamed from: nativeStaticGetKeyARDrone3PilotingSettingsStateMaxAltitudeChangedCurrent */
    private static native String m284xdcb86761();

    /* renamed from: nativeStaticGetKeyARDrone3PilotingSettingsStateMaxAltitudeChangedMax */
    private static native String m285x28ff834c();

    /* renamed from: nativeStaticGetKeyARDrone3PilotingSettingsStateMaxAltitudeChangedMin */
    private static native String m286x28ff843a();

    /* renamed from: nativeStaticGetKeyARDrone3PilotingSettingsStateMaxDistanceChangedCurrent */
    private static native String m287x8c245b74();

    /* renamed from: nativeStaticGetKeyARDrone3PilotingSettingsStateMaxDistanceChangedMax */
    private static native String m288x2ed678df();

    /* renamed from: nativeStaticGetKeyARDrone3PilotingSettingsStateMaxDistanceChangedMin */
    private static native String m289x2ed679cd();

    /* renamed from: nativeStaticGetKeyARDrone3PilotingSettingsStateMaxTiltChangedCurrent */
    private static native String m290x75aeb13c();

    private static native String nativeStaticGetKeyARDrone3PilotingSettingsStateMaxTiltChangedMax();

    private static native String nativeStaticGetKeyARDrone3PilotingSettingsStateMaxTiltChangedMin();

    /* renamed from: nativeStaticGetKeyARDrone3PilotingSettingsStateMinAltitudeChangedCurrent */
    private static native String m291xd6d4e1cf();

    /* renamed from: nativeStaticGetKeyARDrone3PilotingSettingsStateMinAltitudeChangedMax */
    private static native String m292x5d6664ba();

    /* renamed from: nativeStaticGetKeyARDrone3PilotingSettingsStateMinAltitudeChangedMin */
    private static native String m293x5d6665a8();

    /* renamed from: nativeStaticGetKeyARDrone3PilotingSettingsStateNoFlyOverMaxDistanceChangedShouldNotFlyOver */
    private static native String m294x86bab23e();

    /* renamed from: nativeStaticGetKeyARDrone3PilotingSettingsStatePitchModeChangedValue */
    private static native String m295x4e009836();

    private static native String nativeStaticGetKeyARDrone3PilotingStateAirSpeedChangedAirSpeed();

    private static native String nativeStaticGetKeyARDrone3PilotingStateAlertStateChangedState();

    private static native String nativeStaticGetKeyARDrone3PilotingStateAltitudeChangedAltitude();

    private static native String nativeStaticGetKeyARDrone3PilotingStateAttitudeChangedPitch();

    private static native String nativeStaticGetKeyARDrone3PilotingStateAttitudeChangedRoll();

    private static native String nativeStaticGetKeyARDrone3PilotingStateAttitudeChangedYaw();

    /* renamed from: nativeStaticGetKeyARDrone3PilotingStateAutoTakeOffModeChangedState */
    private static native String m296xf06fbc0c();

    private static native String nativeStaticGetKeyARDrone3PilotingStateFlyingStateChangedState();

    /* renamed from: nativeStaticGetKeyARDrone3PilotingStateGpsLocationChangedAltitude */
    private static native String m297x8f17eae4();

    /* renamed from: nativeStaticGetKeyARDrone3PilotingStateGpsLocationChangedAltitudeaccuracy */
    private static native String m298xa3717fdd();

    /* renamed from: nativeStaticGetKeyARDrone3PilotingStateGpsLocationChangedLatitude */
    private static native String m299xbfe04cae();

    /* renamed from: nativeStaticGetKeyARDrone3PilotingStateGpsLocationChangedLatitudeaccuracy */
    private static native String m300x784877a7();

    /* renamed from: nativeStaticGetKeyARDrone3PilotingStateGpsLocationChangedLongitude */
    private static native String m301xa90f71ad();

    /* renamed from: nativeStaticGetKeyARDrone3PilotingStateGpsLocationChangedLongitudeaccuracy */
    private static native String m302x123e2da6();

    private static native String nativeStaticGetKeyARDrone3PilotingStateLandingStateChangedState();

    private static native String nativeStaticGetKeyARDrone3PilotingStateMoveToChangedAltitude();

    private static native String nativeStaticGetKeyARDrone3PilotingStateMoveToChangedHeading();

    private static native String nativeStaticGetKeyARDrone3PilotingStateMoveToChangedLatitude();

    private static native String nativeStaticGetKeyARDrone3PilotingStateMoveToChangedLongitude();

    /* renamed from: nativeStaticGetKeyARDrone3PilotingStateMoveToChangedOrientationmode */
    private static native String m303xfa4dbe18();

    private static native String nativeStaticGetKeyARDrone3PilotingStateMoveToChangedStatus();

    /* renamed from: nativeStaticGetKeyARDrone3PilotingStateNavigateHomeStateChangedReason */
    private static native String m304xbe8ae684();

    /* renamed from: nativeStaticGetKeyARDrone3PilotingStateNavigateHomeStateChangedState */
    private static native String m305xc429e851();

    private static native String nativeStaticGetKeyARDrone3PilotingStatePositionChangedAltitude();

    private static native String nativeStaticGetKeyARDrone3PilotingStatePositionChangedLatitude();

    private static native String nativeStaticGetKeyARDrone3PilotingStatePositionChangedLongitude();

    private static native String nativeStaticGetKeyARDrone3PilotingStateSpeedChangedSpeedX();

    private static native String nativeStaticGetKeyARDrone3PilotingStateSpeedChangedSpeedY();

    private static native String nativeStaticGetKeyARDrone3PilotingStateSpeedChangedSpeedZ();

    private static native String nativeStaticGetKeyARDrone3SettingsStateCPUIDId();

    /* renamed from: nativeStaticGetKeyARDrone3SettingsStateMotorErrorLastErrorChangedMotorError */
    private static native String m306x41c5f63c();

    /* renamed from: nativeStaticGetKeyARDrone3SettingsStateMotorErrorStateChangedMotorError */
    private static native String m307x8b22381d();

    /* renamed from: nativeStaticGetKeyARDrone3SettingsStateMotorErrorStateChangedMotorIds */
    private static native String m308xac3b7e0d();

    /* renamed from: nativeStaticGetKeyARDrone3SettingsStateMotorFlightsStatusChangedLastFlightDuration */
    private static native String m309xcbcd705a();

    /* renamed from: nativeStaticGetKeyARDrone3SettingsStateMotorFlightsStatusChangedNbFlights */
    private static native String m310x5240b7af();

    /* renamed from: nativeStaticGetKeyARDrone3SettingsStateMotorFlightsStatusChangedTotalFlightDuration */
    private static native String m311xb5aa1c88();

    /* renamed from: nativeStaticGetKeyARDrone3SettingsStateMotorSoftwareVersionChangedVersion */
    private static native String m312xda063774();

    private static native String nativeStaticGetKeyARDrone3SettingsStateP7IDSerialID();

    /* renamed from: nativeStaticGetKeyARDrone3SettingsStateProductGPSVersionChangedHardware */
    private static native String m313x79d9edb3();

    /* renamed from: nativeStaticGetKeyARDrone3SettingsStateProductGPSVersionChangedSoftware */
    private static native String m314xc1856972();

    /* renamed from: nativeStaticGetKeyARDrone3SettingsStateProductMotorVersionListChangedHardware */
    private static native String m315xff661080();

    /* renamed from: nativeStaticGetKeyARDrone3SettingsStateProductMotorVersionListChangedMotornumber */
    private static native String m316xf7ead666();

    /* renamed from: nativeStaticGetKeyARDrone3SettingsStateProductMotorVersionListChangedSoftware */
    private static native String m317x47118c3f();

    /* renamed from: nativeStaticGetKeyARDrone3SettingsStateProductMotorVersionListChangedType */
    private static native String m318xbcd1c5d2();

    /* renamed from: nativeStaticGetKeyARDrone3SpeedSettingsStateHullProtectionChangedPresent */
    private static native String m319xe7f9390e();

    /* renamed from: nativeStaticGetKeyARDrone3SpeedSettingsStateMaxPitchRollRotationSpeedChangedCurrent */
    private static native String m320xc915cb4();

    /* renamed from: nativeStaticGetKeyARDrone3SpeedSettingsStateMaxPitchRollRotationSpeedChangedMax */
    private static native String m321xb6761a1f();

    /* renamed from: nativeStaticGetKeyARDrone3SpeedSettingsStateMaxPitchRollRotationSpeedChangedMin */
    private static native String m322xb6761b0d();

    /* renamed from: nativeStaticGetKeyARDrone3SpeedSettingsStateMaxRotationSpeedChangedCurrent */
    private static native String m323xf0c355ab();

    /* renamed from: nativeStaticGetKeyARDrone3SpeedSettingsStateMaxRotationSpeedChangedMax */
    private static native String m324xe8812696();

    /* renamed from: nativeStaticGetKeyARDrone3SpeedSettingsStateMaxRotationSpeedChangedMin */
    private static native String m325xe8812784();

    /* renamed from: nativeStaticGetKeyARDrone3SpeedSettingsStateMaxVerticalSpeedChangedCurrent */
    private static native String m326x98624ab3();

    /* renamed from: nativeStaticGetKeyARDrone3SpeedSettingsStateMaxVerticalSpeedChangedMax */
    private static native String m327x49b3df9e();

    /* renamed from: nativeStaticGetKeyARDrone3SpeedSettingsStateMaxVerticalSpeedChangedMin */
    private static native String m328x49b3e08c();

    /* renamed from: nativeStaticGetKeyARDrone3SpeedSettingsStateOutdoorChangedOutdoor */
    private static native String m329xaa007ee3();

    static {
        f1273x28dc0c4c = "";
        f1272xfdbaddc5 = "";
        f1279x8c9714c9 = "";
        f1278xcf72e02 = "";
        f1271xfd34e368 = "";
        f1270xfc6ef0df = "";
        f1277x5e4fc0a5 = "";
        f1276x5d89ce1c = "";
        f1275xb7bc32b4 = "";
        f1274x5c49843 = "";
        f1267xe94aa7b5 = "";
        f1266xe9490723 = "";
        f1269x4c924e72 = "";
        f1268x4c90ade0 = "";
        f1351xa0666478 = "";
        f1345x987bdc7d = "";
        f1366xb63e0a79 = "";
        f1365xefb093c = "";
        f1368x545b0e54 = "";
        f1369xa3ece4a7 = "";
        f1367x2392ac8a = "";
        f1370x44e46b0f = "";
        f1371x44e46b10 = "";
        f1372x44e46b11 = "";
        f1348x2e557066 = "";
        f1347x9c39d057 = "";
        f1349x438f3d46 = "";
        f1350xcb8a97fe = "";
        f1346x632b4c03 = "";
        f1354x1c854c02 = "";
        f1356xe10a5cb9 = "";
        f1352xebbcea38 = "";
        f1355x943ef096 = "";
        f1357x4cebbdbf = "";
        f1353xce36ef20 = "";
        f1358x9140a578 = "";
        f1344x4eadab39 = "";
        f1361xe556ba37 = "";
        f1362x3266b524 = "";
        f1359xb48e586d = "";
        f1363x7d37119d = "";
        f1360xe6fd86b7 = "";
        f1364x4c98d87d = "";
        ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_PILOTINGEVENT_MOVEBYEND_DX = "";
        ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_PILOTINGEVENT_MOVEBYEND_DY = "";
        ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_PILOTINGEVENT_MOVEBYEND_DZ = "";
        f1314xffe37e19 = "";
        f1315xfc9b4291 = "";
        f1295x2e764e56 = "";
        f1294x2e75db32 = "";
        f1292x2e6e5110 = "";
        f1293x77d20428 = "";
        f1289x71387744 = "";
        f1290xda25c174 = "";
        f1291x590eb7d = "";
        f1330xde69209f = "";
        f1332x64dd9478 = "";
        f1331x64dd938a = "";
        f1336xf28ed8a4 = "";
        f1338x5bc216fd = "";
        f1337x5bc2160f = "";
        f1316x7a02e1f0 = "";
        f1333x1c7baeec = "";
        f1335x19e55145 = "";
        f1334x19e55057 = "";
        f1342x3934425c = "";
        f1318x3fe51808 = "";
        f1321xf0281236 = "";
        f1317xeb8b9523 = "";
        f1320x5d516b35 = "";
        f1319x8cdf952e = "";
        f1322x840ca3f7 = "";
        f1339x27dbf3f1 = "";
        f1341xbd52e0ca = "";
        f1340xbd52dfdc = "";
        f1326xd0709fcb = "";
        f1327x6c72ae78 = "";
        f1329x3b01f6d1 = "";
        f1328x3b01f5e3 = "";
        f1323xbb453b68 = "";
        f1325xa7807bc1 = "";
        f1324xa7807ad3 = "";
        f1343xbabbb61a = "";
        f1393xea570f45 = "";
        f1395x92a8c61e = "";
        f1394x92a8c530 = "";
        f1390x9e17634d = "";
        f1392xcd845e26 = "";
        f1391xcd845d38 = "";
        f1386x535dfc2e = "";
        f1396xc37bf971 = "";
        f1387x219c81a = "";
        f1389xb2eb3173 = "";
        f1388xb2eb3085 = "";
        f1288xf8449199 = "";
        f1286xf83c0894 = "";
        f1287x9250ee24 = "";
        f1282x95562c1d = "";
        f1285x706c8d01 = "";
        f1283x45b0c798 = "";
        f1284xea7ac2d2 = "";
        f1383x879da021 = "";
        f1385xe57742e8 = "";
        f1384x77d78655 = "";
        f1382x302c0a96 = "";
        f1381x22947ce2 = "";
        f1380xdae90123 = "";
        f1375x10c03ebf = "";
        f1374xe179904f = "";
        f1379x158c4684 = "";
        f1377xbd841497 = "";
        f1376xcedb3c32 = "";
        f1378x88397310 = "";
        f1373xfc134ab0 = "";
        ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_SETTINGSSTATE_P7ID_SERIALID = "";
        ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_SETTINGSSTATE_CPUID_ID = "";
        f1300x4c479c27 = "";
        f1296xc4ea6e5a = "";
        f1299x226e117 = "";
        f1298xa928ab78 = "";
        f1297xa928aa8a = "";
        f1303xa8cee7e1 = "";
        f1302xfed7dcc2 = "";
        f1301xfed7dbd4 = "";
        f1304x82341529 = "";
        f1305x846c97bd = "";
        f1307x365566ff = "";
        f1306x5eb63411 = "";
        f1308xd73128e = "";
        f1309xb745b217 = "";
        f1313x6f00228 = "";
        f1311x9a09223a = "";
        f1310xc2c901dd = "";
        f1312x89c9db84 = "";
        f1280xe09a2160 = "";
        f1281x23eb31e4 = "";
        f1255xcd6b0bd5 = "";
        f1256x4cdc9746 = "";
        f1254x9ca2aa0b = "";
        f1259xbc8c0466 = "";
        f1260x41dab0d5 = "";
        f1258x8bc3a29c = "";
        f1252xa0f99742 = "";
        f1253x17608929 = "";
        f1257x35abd45d = "";
        f1261xad6ef403 = "";
        f1250x424e0c0b = "";
        f1251x74599dd0 = "";
        f1247xa619df1b = "";
        ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_CAMERASTATE_ORIENTATION_PAN = "";
        f1244x5a8a05a3 = "";
        f1243x768884d7 = "";
        f1246x723b323f = "";
        f1245xbf154bb = "";
        f1242xcb3bd6c7 = "";
        f1241xe585ff33 = "";
        f1249xe45743c6 = "";
        f1248xf6d97654 = "";
        f1239xb6705588 = "";
        f1240xc4ef3227 = "";
        f1265xef8be551 = "";
        f1263x2f6b1155 = "";
        f1262x7cf296e = "";
        f1264x384e5cac = "";
        ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_PROSTATE_FEATURES_FEATURES = "";
        f1236xcd22990f = "";
        f1235x7de0aa5b = "";
        f1238xd730b6bc = "";
        f1237xf2c43b60 = "";
        f1273x28dc0c4c = m234xb367f926();
        f1272xfdbaddc5 = m233x2ebaf7f7();
        f1279x8c9714c9 = nativeStaticGetKeyARDrone3MediaRecordStateVideoStateChangedState();
        f1278xcf72e02 = m239x9b54849a();
        f1271xfd34e368 = m236xa69bd6ca();
        f1270xfc6ef0df = m235xa5d5e441();
        f1277x5e4fc0a5 = m241x35f859ad();
        f1276x5d89ce1c = m240x35326724();
        f1275xb7bc32b4 = m238x6075b2e2();
        f1274x5c49843 = m237xae7e1871();
        f1267xe94aa7b5 = m232x5aa9f2c1();
        f1266xe9490723 = m231x5aa8522f();
        f1269x4c924e72 = nativeStaticGetKeyARDrone3MediaRecordEventVideoEventChangedEvent();
        f1268x4c90ade0 = nativeStaticGetKeyARDrone3MediaRecordEventVideoEventChangedError();
        f1351xa0666478 = nativeStaticGetKeyARDrone3PilotingStateFlyingStateChangedState();
        f1345x987bdc7d = nativeStaticGetKeyARDrone3PilotingStateAlertStateChangedState();
        f1366xb63e0a79 = m305xc429e851();
        f1365xefb093c = m304xbe8ae684();
        f1368x545b0e54 = nativeStaticGetKeyARDrone3PilotingStatePositionChangedLatitude();
        f1369xa3ece4a7 = nativeStaticGetKeyARDrone3PilotingStatePositionChangedLongitude();
        f1367x2392ac8a = nativeStaticGetKeyARDrone3PilotingStatePositionChangedAltitude();
        f1370x44e46b0f = nativeStaticGetKeyARDrone3PilotingStateSpeedChangedSpeedX();
        f1371x44e46b10 = nativeStaticGetKeyARDrone3PilotingStateSpeedChangedSpeedY();
        f1372x44e46b11 = nativeStaticGetKeyARDrone3PilotingStateSpeedChangedSpeedZ();
        f1348x2e557066 = nativeStaticGetKeyARDrone3PilotingStateAttitudeChangedRoll();
        f1347x9c39d057 = nativeStaticGetKeyARDrone3PilotingStateAttitudeChangedPitch();
        f1349x438f3d46 = nativeStaticGetKeyARDrone3PilotingStateAttitudeChangedYaw();
        f1350xcb8a97fe = m296xf06fbc0c();
        f1346x632b4c03 = nativeStaticGetKeyARDrone3PilotingStateAltitudeChangedAltitude();
        f1354x1c854c02 = m299xbfe04cae();
        f1356xe10a5cb9 = m301xa90f71ad();
        f1352xebbcea38 = m297x8f17eae4();
        f1355x943ef096 = m300x784877a7();
        f1357x4cebbdbf = m302x123e2da6();
        f1353xce36ef20 = m298xa3717fdd();
        f1358x9140a578 = nativeStaticGetKeyARDrone3PilotingStateLandingStateChangedState();
        f1344x4eadab39 = nativeStaticGetKeyARDrone3PilotingStateAirSpeedChangedAirSpeed();
        f1361xe556ba37 = nativeStaticGetKeyARDrone3PilotingStateMoveToChangedLatitude();
        f1362x3266b524 = nativeStaticGetKeyARDrone3PilotingStateMoveToChangedLongitude();
        f1359xb48e586d = nativeStaticGetKeyARDrone3PilotingStateMoveToChangedAltitude();
        f1363x7d37119d = m303xfa4dbe18();
        f1360xe6fd86b7 = nativeStaticGetKeyARDrone3PilotingStateMoveToChangedHeading();
        f1364x4c98d87d = nativeStaticGetKeyARDrone3PilotingStateMoveToChangedStatus();
        ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_PILOTINGEVENT_MOVEBYEND_DX = nativeStaticGetKeyARDrone3PilotingEventMoveByEndDX();
        ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_PILOTINGEVENT_MOVEBYEND_DY = nativeStaticGetKeyARDrone3PilotingEventMoveByEndDY();
        ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_PILOTINGEVENT_MOVEBYEND_DZ = nativeStaticGetKeyARDrone3PilotingEventMoveByEndDZ();
        f1314xffe37e19 = nativeStaticGetKeyARDrone3PilotingEventMoveByEndDPsi();
        f1315xfc9b4291 = nativeStaticGetKeyARDrone3PilotingEventMoveByEndError();
        f1295x2e764e56 = nativeStaticGetKeyARDrone3NetworkStateWifiScanListChangedSsid();
        f1294x2e75db32 = nativeStaticGetKeyARDrone3NetworkStateWifiScanListChangedRssi();
        f1292x2e6e5110 = nativeStaticGetKeyARDrone3NetworkStateWifiScanListChangedBand();
        f1293x77d20428 = nativeStaticGetKeyARDrone3NetworkStateWifiScanListChangedChannel();
        f1289x71387744 = m249x523698e2();
        f1290xda25c174 = m250x7fb04f36();
        f1291x590eb7d = m251xc819e6b9();
        f1330xde69209f = m284xdcb86761();
        f1332x64dd9478 = m286x28ff843a();
        f1331x64dd938a = m285x28ff834c();
        f1336xf28ed8a4 = m290x75aeb13c();
        f1338x5bc216fd = nativeStaticGetKeyARDrone3PilotingSettingsStateMaxTiltChangedMin();
        f1337x5bc2160f = nativeStaticGetKeyARDrone3PilotingSettingsStateMaxTiltChangedMax();
        f1316x7a02e1f0 = m270xcdebfe9a();
        f1333x1c7baeec = m287x8c245b74();
        f1335x19e55145 = m289x2ed679cd();
        f1334x19e55057 = m288x2ed678df();
        f1342x3934425c = m294x86bab23e();
        f1318x3fe51808 = m272x4d2fbb08();
        f1321xf0281236 = m275xead0e65a();
        f1317xeb8b9523 = m271x3dec2973();
        f1320x5d516b35 = m274x51d4fde1();
        f1319x8cdf952e = m273xf8212462();
        f1322x840ca3f7 = m276x1007a39f();
        f1339x27dbf3f1 = m291xd6d4e1cf();
        f1341xbd52e0ca = m293x5d6665a8();
        f1340xbd52dfdc = m292x5d6664ba();
        f1326xd0709fcb = m280x27ce7325();
        f1327x6c72ae78 = m281x8d71814e();
        f1329x3b01f6d1 = m283x8c049ca7();
        f1328x3b01f5e3 = m282x8c049bb9();
        f1323xbb453b68 = m277xe644619e();
        f1325xa7807bc1 = m279xb37424f7();
        f1324xa7807ad3 = m278xb3742409();
        f1343xbabbb61a = m295x4e009836();
        f1393xea570f45 = m326x98624ab3();
        f1395x92a8c61e = m328x49b3e08c();
        f1394x92a8c530 = m327x49b3df9e();
        f1390x9e17634d = m323xf0c355ab();
        f1392xcd845e26 = m325xe8812784();
        f1391xcd845d38 = m324xe8812696();
        f1386x535dfc2e = m319xe7f9390e();
        f1396xc37bf971 = m329xaa007ee3();
        f1387x219c81a = m320xc915cb4();
        f1389xb2eb3173 = m322xb6761b0d();
        f1388xb2eb3085 = m321xb6761a1f();
        f1288xf8449199 = m248x1c410411();
        f1286xf83c0894 = m246x1c387b0c();
        f1287x9250ee24 = m247x50de44cc();
        f1282x95562c1d = m244xb0fda19f();
        f1285x706c8d01 = nativeStaticGetKeyARDrone3NetworkSettingsStateWifiSecurityType();
        f1283x45b0c798 = nativeStaticGetKeyARDrone3NetworkSettingsStateWifiSecurityKey();
        f1284xea7ac2d2 = m245xb55b804a();
        f1383x879da021 = m316xf7ead666();
        f1385xe57742e8 = m318xbcd1c5d2();
        f1384x77d78655 = m317x47118c3f();
        f1382x302c0a96 = m315xff661080();
        f1381x22947ce2 = m314xc1856972();
        f1380xdae90123 = m313x79d9edb3();
        f1375x10c03ebf = m308xac3b7e0d();
        f1374xe179904f = m307x8b22381d();
        f1379x158c4684 = m312xda063774();
        f1377xbd841497 = m310x5240b7af();
        f1376xcedb3c32 = m309xcbcd705a();
        f1378x88397310 = m311xb5aa1c88();
        f1373xfc134ab0 = m306x41c5f63c();
        ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_SETTINGSSTATE_P7ID_SERIALID = nativeStaticGetKeyARDrone3SettingsStateP7IDSerialID();
        ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_SETTINGSSTATE_CPUID_ID = nativeStaticGetKeyARDrone3SettingsStateCPUIDId();
        f1300x4c479c27 = m256x836d93e3();
        f1296xc4ea6e5a = m252x1b145702();
        f1299x226e117 = m255x19ad6843();
        f1298xa928ab78 = m254x48e09a4();
        f1297xa928aa8a = m253x48e08b6();
        f1303xa8cee7e1 = m259x79e46039();
        f1302xfed7dcc2 = m258x7aee871a();
        f1301xfed7dbd4 = m257x7aee862c();
        f1304x82341529 = m260xc48ada3();
        f1305x846c97bd = m261x3ceb0ea3();
        f1307x365566ff = m263xfefe9e99();
        f1306x5eb63411 = m262x275f6bab();
        f1308xd73128e = m264x6f67bcfe();
        f1309xb745b217 = m265x8926585f();
        f1313x6f00228 = m269x591518a6();
        f1311x9a09223a = m267x306ed2d4();
        f1310xc2c901dd = m266x9e31df55();
        f1312x89c9db84 = m268xe715b4b8();
        f1280xe09a2160 = m242x24496812();
        f1281x23eb31e4 = m243xabcd97b2();
        f1255xcd6b0bd5 = nativeStaticGetKeyARDrone3GPSSettingsStateHomeChangedLatitude();
        f1256x4cdc9746 = nativeStaticGetKeyARDrone3GPSSettingsStateHomeChangedLongitude();
        f1254x9ca2aa0b = nativeStaticGetKeyARDrone3GPSSettingsStateHomeChangedAltitude();
        f1259xbc8c0466 = m225x57381d10();
        f1260x41dab0d5 = m226xfcb1ad8b();
        f1258x8bc3a29c = m224x266fbb46();
        f1252xa0f99742 = m220xfc9fca48();
        f1253x17608929 = m221x496dc299();
        f1257x35abd45d = nativeStaticGetKeyARDrone3GPSSettingsStateHomeTypeChangedType();
        f1261xad6ef403 = m227x7a9d72a5();
        f1250x424e0c0b = m222x75d946cd();
        f1251x74599dd0 = m223xb235bb6e();
        f1247xa619df1b = nativeStaticGetKeyARDrone3CameraStateOrientationTilt();
        ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_CAMERASTATE_ORIENTATION_PAN = nativeStaticGetKeyARDrone3CameraStateOrientationPan();
        f1244x5a8a05a3 = m217xe3ae2599();
        f1243x768884d7 = nativeStaticGetKeyARDrone3CameraStateDefaultCameraOrientationPan();
        f1246x723b323f = nativeStaticGetKeyARDrone3CameraStateOrientationV2Tilt();
        f1245xbf154bb = nativeStaticGetKeyARDrone3CameraStateOrientationV2Pan();
        f1242xcb3bd6c7 = m219xb112c335();
        f1241xe585ff33 = m218x92195125();
        f1249xe45743c6 = nativeStaticGetKeyARDrone3CameraStateVelocityRangeMaxtilt();
        f1248xf6d97654 = nativeStaticGetKeyARDrone3CameraStateVelocityRangeMaxpan();
        f1239xb6705588 = m216x2ebf21fc();
        f1240xc4ef3227 = nativeStaticGetKeyARDrone3AntiflickeringStateModeChangedMode();
        f1265xef8be551 = m230x95c33941();
        f1263x2f6b1155 = m229x31ec77b9();
        f1262x7cf296e = m228x28b419aa();
        f1264x384e5cac = nativeStaticGetKeyARDrone3GPSStateHomeTypeChosenChangedType();
        ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_PROSTATE_FEATURES_FEATURES = nativeStaticGetKeyARDrone3PROStateFeaturesFeatures();
        f1236xcd22990f = nativeStaticGetKeyARDrone3AccessoryStateConnectedAccessoriesId();
        f1235x7de0aa5b = m213xc821a94c();
        f1238xd730b6bc = nativeStaticGetKeyARDrone3AccessoryStateConnectedAccessoriesUid();
        f1237xf2c43b60 = m215x404711d4();
    }

    public ARFeatureARDrone3(long nativeFeature) {
        if (nativeFeature != 0) {
            this.jniFeature = nativeFeature;
            this.initOk = true;
        }
    }

    public void dispose() {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                this.jniFeature = 0;
                this.initOk = false;
            }
        }
    }

    public void finalize() throws Throwable {
        try {
            dispose();
        } finally {
            super.finalize();
        }
    }

    public ARCONTROLLER_ERROR_ENUM sendPilotingFlatTrim() {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendPilotingFlatTrim(this.jniFeature));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendPilotingTakeOff() {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendPilotingTakeOff(this.jniFeature));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendPilotingPCMD(byte _flag, byte _roll, byte _pitch, byte _yaw, byte _gaz, int _timestampAndSeqNum) {
        ARFeatureARDrone3 aRFeatureARDrone3 = this;
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            try {
                if (aRFeatureARDrone3.initOk) {
                    int nativeError = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendPilotingPCMD(aRFeatureARDrone3.jniFeature, _flag, _roll, _pitch, _yaw, _gaz, _timestampAndSeqNum));
                }
            } finally {
                Object obj = r0;
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM setPilotingPCMD(byte _flag, byte _roll, byte _pitch, byte _yaw, byte _gaz, int _timestampAndSeqNum) {
        ARFeatureARDrone3 aRFeatureARDrone3 = this;
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            try {
                if (aRFeatureARDrone3.initOk) {
                    int nativeError = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSetPilotingPCMD(aRFeatureARDrone3.jniFeature, _flag, _roll, _pitch, _yaw, _gaz, _timestampAndSeqNum));
                }
            } finally {
                Object obj = r0;
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM setPilotingPCMDFlag(byte _flag) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSetPilotingPCMDFlag(this.jniFeature, _flag));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM setPilotingPCMDRoll(byte _roll) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSetPilotingPCMDRoll(this.jniFeature, _roll));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM setPilotingPCMDPitch(byte _pitch) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSetPilotingPCMDPitch(this.jniFeature, _pitch));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM setPilotingPCMDYaw(byte _yaw) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSetPilotingPCMDYaw(this.jniFeature, _yaw));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM setPilotingPCMDGaz(byte _gaz) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSetPilotingPCMDGaz(this.jniFeature, _gaz));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM setPilotingPCMDTimestampAndSeqNum(int _timestampAndSeqNum) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSetPilotingPCMDTimestampAndSeqNum(this.jniFeature, _timestampAndSeqNum));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendPilotingLanding() {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendPilotingLanding(this.jniFeature));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendPilotingEmergency() {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendPilotingEmergency(this.jniFeature));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendPilotingNavigateHome(byte _start) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendPilotingNavigateHome(this.jniFeature, _start));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendPilotingAutoTakeOffMode(byte _state) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendPilotingAutoTakeOffMode(this.jniFeature, _state));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendPilotingMoveBy(float _dX, float _dY, float _dZ, float _dPsi) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendPilotingMoveBy(this.jniFeature, _dX, _dY, _dZ, _dPsi));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendPilotingUserTakeOff(byte _state) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendPilotingUserTakeOff(this.jniFeature, _state));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendPilotingCircle(ARCOMMANDS_ARDRONE3_PILOTING_CIRCLE_DIRECTION_ENUM _direction) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendPilotingCircle(this.jniFeature, _direction.getValue()));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendPilotingMoveTo(double _latitude, double _longitude, double _altitude, ARCOMMANDS_ARDRONE3_PILOTING_MOVETO_ORIENTATION_MODE_ENUM _orientation_mode, float _heading) {
        ARFeatureARDrone3 aRFeatureARDrone3 = this;
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            try {
                if (aRFeatureARDrone3.initOk) {
                    int nativeError = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendPilotingMoveTo(aRFeatureARDrone3.jniFeature, _latitude, _longitude, _altitude, _orientation_mode.getValue(), _heading));
                }
            } finally {
                Object obj = r0;
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendPilotingCancelMoveTo() {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendPilotingCancelMoveTo(this.jniFeature));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendAnimationsFlip(ARCOMMANDS_ARDRONE3_ANIMATIONS_FLIP_DIRECTION_ENUM _direction) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendAnimationsFlip(this.jniFeature, _direction.getValue()));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendCameraOrientation(byte _tilt, byte _pan) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendCameraOrientation(this.jniFeature, _tilt, _pan));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM setCameraOrientation(byte _tilt, byte _pan) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSetCameraOrientation(this.jniFeature, _tilt, _pan));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM setCameraOrientationTilt(byte _tilt) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSetCameraOrientationTilt(this.jniFeature, _tilt));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM setCameraOrientationPan(byte _pan) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSetCameraOrientationPan(this.jniFeature, _pan));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendCameraOrientationV2(float _tilt, float _pan) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendCameraOrientationV2(this.jniFeature, _tilt, _pan));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM setCameraOrientationV2(float _tilt, float _pan) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSetCameraOrientationV2(this.jniFeature, _tilt, _pan));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM setCameraOrientationV2Tilt(float _tilt) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSetCameraOrientationV2Tilt(this.jniFeature, _tilt));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM setCameraOrientationV2Pan(float _pan) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSetCameraOrientationV2Pan(this.jniFeature, _pan));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendCameraVelocity(float _tilt, float _pan) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendCameraVelocity(this.jniFeature, _tilt, _pan));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM setCameraVelocity(float _tilt, float _pan) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSetCameraVelocity(this.jniFeature, _tilt, _pan));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM setCameraVelocityTilt(float _tilt) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSetCameraVelocityTilt(this.jniFeature, _tilt));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM setCameraVelocityPan(float _pan) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSetCameraVelocityPan(this.jniFeature, _pan));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendMediaRecordPicture(byte _mass_storage_id) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendMediaRecordPicture(this.jniFeature, _mass_storage_id));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendMediaRecordVideo(ARCOMMANDS_ARDRONE3_MEDIARECORD_VIDEO_RECORD_ENUM _record, byte _mass_storage_id) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendMediaRecordVideo(this.jniFeature, _record.getValue(), _mass_storage_id));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendMediaRecordPictureV2() {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendMediaRecordPictureV2(this.jniFeature));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendMediaRecordVideoV2(ARCOMMANDS_ARDRONE3_MEDIARECORD_VIDEOV2_RECORD_ENUM _record) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendMediaRecordVideoV2(this.jniFeature, _record.getValue()));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendNetworkWifiScan(ARCOMMANDS_ARDRONE3_NETWORK_WIFISCAN_BAND_ENUM _band) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendNetworkWifiScan(this.jniFeature, _band.getValue()));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendNetworkWifiAuthChannel() {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendNetworkWifiAuthChannel(this.jniFeature));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendPilotingSettingsMaxAltitude(float _current) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendPilotingSettingsMaxAltitude(this.jniFeature, _current));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendPilotingSettingsMaxTilt(float _current) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendPilotingSettingsMaxTilt(this.jniFeature, _current));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendPilotingSettingsAbsolutControl(byte _on) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendPilotingSettingsAbsolutControl(this.jniFeature, _on));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendPilotingSettingsMaxDistance(float _value) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendPilotingSettingsMaxDistance(this.jniFeature, _value));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendPilotingSettingsNoFlyOverMaxDistance(byte _shouldNotFlyOver) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendPilotingSettingsNoFlyOverMaxDistance(this.jniFeature, _shouldNotFlyOver));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendPilotingSettingsSetAutonomousFlightMaxHorizontalSpeed(float _value) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendPilotingSettingsSetAutonomousFlightMaxHorizontalSpeed(this.jniFeature, _value));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendPilotingSettingsSetAutonomousFlightMaxVerticalSpeed(float _value) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendPilotingSettingsSetAutonomousFlightMaxVerticalSpeed(this.jniFeature, _value));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendPilotingSettingsSetAutonomousFlightMaxHorizontalAcceleration(float _value) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(m211x18ed7fd2(this.jniFeature, _value));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendPilotingSettingsSetAutonomousFlightMaxVerticalAcceleration(float _value) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(m212xae9cdba4(this.jniFeature, _value));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendPilotingSettingsSetAutonomousFlightMaxRotationSpeed(float _value) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendPilotingSettingsSetAutonomousFlightMaxRotationSpeed(this.jniFeature, _value));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendPilotingSettingsBankedTurn(byte _value) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendPilotingSettingsBankedTurn(this.jniFeature, _value));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendPilotingSettingsMinAltitude(float _current) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendPilotingSettingsMinAltitude(this.jniFeature, _current));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendPilotingSettingsCirclingDirection(C1402xbc6011c1 _value) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendPilotingSettingsCirclingDirection(this.jniFeature, _value.getValue()));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendPilotingSettingsCirclingRadius(short _value) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendPilotingSettingsCirclingRadius(this.jniFeature, _value));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendPilotingSettingsCirclingAltitude(short _value) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendPilotingSettingsCirclingAltitude(this.jniFeature, _value));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendPilotingSettingsPitchMode(ARCOMMANDS_ARDRONE3_PILOTINGSETTINGS_PITCHMODE_VALUE_ENUM _value) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendPilotingSettingsPitchMode(this.jniFeature, _value.getValue()));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendSpeedSettingsMaxVerticalSpeed(float _current) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendSpeedSettingsMaxVerticalSpeed(this.jniFeature, _current));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendSpeedSettingsMaxRotationSpeed(float _current) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendSpeedSettingsMaxRotationSpeed(this.jniFeature, _current));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendSpeedSettingsHullProtection(byte _present) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendSpeedSettingsHullProtection(this.jniFeature, _present));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendSpeedSettingsOutdoor(byte _outdoor) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendSpeedSettingsOutdoor(this.jniFeature, _outdoor));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendSpeedSettingsMaxPitchRollRotationSpeed(float _current) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendSpeedSettingsMaxPitchRollRotationSpeed(this.jniFeature, _current));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendNetworkSettingsWifiSelection(ARCOMMANDS_ARDRONE3_NETWORKSETTINGS_WIFISELECTION_TYPE_ENUM _type, ARCOMMANDS_ARDRONE3_NETWORKSETTINGS_WIFISELECTION_BAND_ENUM _band, byte _channel) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendNetworkSettingsWifiSelection(this.jniFeature, _type.getValue(), _band.getValue(), _channel));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendNetworkSettingsWifiSecurity(ARCOMMANDS_ARDRONE3_NETWORKSETTINGS_WIFISECURITY_TYPE_ENUM _type, String _key, ARCOMMANDS_ARDRONE3_NETWORKSETTINGS_WIFISECURITY_KEYTYPE_ENUM _keyType) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendNetworkSettingsWifiSecurity(this.jniFeature, _type.getValue(), _key, _keyType.getValue()));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendPictureSettingsPictureFormatSelection(C1397x91f54a09 _type) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendPictureSettingsPictureFormatSelection(this.jniFeature, _type.getValue()));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendPictureSettingsAutoWhiteBalanceSelection(C1396x10f9ec46 _type) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendPictureSettingsAutoWhiteBalanceSelection(this.jniFeature, _type.getValue()));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendPictureSettingsExpositionSelection(float _value) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendPictureSettingsExpositionSelection(this.jniFeature, _value));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendPictureSettingsSaturationSelection(float _value) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendPictureSettingsSaturationSelection(this.jniFeature, _value));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendPictureSettingsTimelapseSelection(byte _enabled, float _interval) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendPictureSettingsTimelapseSelection(this.jniFeature, _enabled, _interval));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendPictureSettingsVideoAutorecordSelection(byte _enabled, byte _mass_storage_id) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendPictureSettingsVideoAutorecordSelection(this.jniFeature, _enabled, _mass_storage_id));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendPictureSettingsVideoStabilizationMode(C1399x62185770 _mode) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendPictureSettingsVideoStabilizationMode(this.jniFeature, _mode.getValue()));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendPictureSettingsVideoRecordingMode(ARCOMMANDS_ARDRONE3_PICTURESETTINGS_VIDEORECORDINGMODE_MODE_ENUM _mode) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendPictureSettingsVideoRecordingMode(this.jniFeature, _mode.getValue()));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendPictureSettingsVideoFramerate(C1398xcc3fb1b5 _framerate) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendPictureSettingsVideoFramerate(this.jniFeature, _framerate.getValue()));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendPictureSettingsVideoResolutions(ARCOMMANDS_ARDRONE3_PICTURESETTINGS_VIDEORESOLUTIONS_TYPE_ENUM _type) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendPictureSettingsVideoResolutions(this.jniFeature, _type.getValue()));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendMediaStreamingVideoEnable(byte _enable) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendMediaStreamingVideoEnable(this.jniFeature, _enable));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendMediaStreamingVideoStreamMode(ARCOMMANDS_ARDRONE3_MEDIASTREAMING_VIDEOSTREAMMODE_MODE_ENUM _mode) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendMediaStreamingVideoStreamMode(this.jniFeature, _mode.getValue()));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendGPSSettingsSetHome(double _latitude, double _longitude, double _altitude) {
        ARFeatureARDrone3 aRFeatureARDrone3 = this;
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            try {
                if (aRFeatureARDrone3.initOk) {
                    int nativeError = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendGPSSettingsSetHome(aRFeatureARDrone3.jniFeature, _latitude, _longitude, _altitude));
                }
            } finally {
                Object obj = r0;
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendGPSSettingsResetHome() {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendGPSSettingsResetHome(this.jniFeature));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendGPSSettingsSendControllerGPS(double _latitude, double _longitude, double _altitude, double _horizontalAccuracy, double _verticalAccuracy) {
        ARFeatureARDrone3 aRFeatureARDrone3 = this;
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            try {
                if (aRFeatureARDrone3.initOk) {
                    int nativeError = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendGPSSettingsSendControllerGPS(aRFeatureARDrone3.jniFeature, _latitude, _longitude, _altitude, _horizontalAccuracy, _verticalAccuracy));
                }
            } finally {
                Object obj = r0;
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendGPSSettingsHomeType(ARCOMMANDS_ARDRONE3_GPSSETTINGS_HOMETYPE_TYPE_ENUM _type) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendGPSSettingsHomeType(this.jniFeature, _type.getValue()));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendGPSSettingsReturnHomeDelay(short _delay) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendGPSSettingsReturnHomeDelay(this.jniFeature, _delay));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendAntiflickeringElectricFrequency(C1369x263150ea _frequency) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendAntiflickeringElectricFrequency(this.jniFeature, _frequency.getValue()));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendAntiflickeringSetMode(ARCOMMANDS_ARDRONE3_ANTIFLICKERING_SETMODE_MODE_ENUM _mode) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendAntiflickeringSetMode(this.jniFeature, _mode.getValue()));
            }
        }
        return error;
    }
}
