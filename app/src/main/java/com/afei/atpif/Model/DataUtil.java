package com.afei.atpif.Model;

/**
 * Created by afei on 2017/7/31.
 */

public abstract class DataUtil {


/****************************************
 F1   0x03      29.0Hz        F2   0x05      27.9Hz
 F3   0x09      26.8Hz        F4   0x11      25.7Hz
 F5   0x21      24.6Hz        F6   0x06      23.5Hz
 F7   0x0a      22.4Hz        F8   0x12      21.3Hz
 F9   0x22      20.2Hz        F10  0x0c      19.1Hz
 F11  0x14      18.0Hz        F12  0x24      16.9Hz
 F13  0x18      15.8Hz        F14  0x28      14.7Hz
 F15  0x30      13.6Hz        F16  0x3c      12.5Hz
 F17  0x3a      11.4Hz        F18  0x36      10.3Hz
 F19  0x2e       9.2Hz        F20  0x1e       8.1Hz
 F21  0x39       7.0Hz        F22  0x35       5.9Hz
 F23  0x2d       4.8Hz        F24  0x1d      30.1Hz
 F25  0x33      31.2Hz        F26  0x2b      32.3Hz
 F27  0x1b      33.4Hz        F28  0x27      34.5Hz
 ******************************************/
public static final char  LF_ARRAY[] = {
            0X03,   /*   29.0Hz    */
            0X05,   /*   27.9Hz    */
            0X09,   /*   26.8Hz    */
            0X11,   /*   25.7Hz    */
            0X21,   /*   24.6Hz    */
            0X06,   /*   23.5Hz    */
            0X0A,   /*   22.4Hz    */
            0X12,   /*   21.3Hz    */
            0X22,   /*   20.2Hz    */
            0X0C,   /*   19.1Hz    */
            0X14,   /*   18.0Hz    */
            0X24,   /*   16.9Hz    */
            0X18,   /*   15.8Hz    */
            0X28,   /*   14.7Hz    */
            0X30,   /*   13.6Hz    */
            0X3C,   /*   12.5Hz    */
            0X3A,   /*   11.4Hz    */
            0X36,   /*   10.3Hz    */
            0x00,   /*   无码      */
    };


}
