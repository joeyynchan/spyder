/*****************************************************************************
 *  FILE:  anytimetz.js - The Any+Time(TM) JavaScript Library
 *                        Basic Time Zone Support (source)
 *
 *  VERSION: 5.0.1-1402221402
 *
 *  Copyright 2008-2014 Andrew M. Andrews III (www.AMA3.com). Some Rights
 *  Reserved. This work licensed under the Creative Commons Attribution-
 *  Noncommercial-Share Alike 3.0 Unported License except in jurisdicitons
 *  for which the license has been ported by Creative Commons International,
 *  where the work is licensed under the applicable ported license instead.
 *  For a copy of the unported license, visit
 *  http://creativecommons.org/licenses/by-nc-sa/3.0/
 *  or send a letter to Creative Commons, 171 Second Street, Suite 300,
 *  San Francisco, California, 94105, USA.  For ported versions of the
 *  license, visit http://creativecommons.org/international/
 *
 *  Alternative licensing arrangements may be made by contacting the
 *  author at http://www.AMA3.com/contact/
 *
 *  This file adds basic labels for major time zones to the Any+Time(TM)
 *  JavaScript Library.  Time zone support is extremely complicated, and
 *  ECMA-262 (JavaScript) provides little support.  Developers are expected
 *  to tailor this file to meet their needs, mostly by removing lines that
 *  are not required by their users, and/or by removing either abbreviated
 *  (before double-dash) or long (after double-dash) names from the strings.
 *
 *  Note that there is no automatic detection of daylight savings time
 *  (AKA summer time), due to lack of support in JavaScript and the
 *  time-prohibitive complexity of attempting such support in code.
 *  If you want to take a stab at it, let me know; if you want to pay me
 *  large sums of money to add it, again, let me know. :-p
 *
 *  This file should be included AFTER anytime.js (or anytimec.js) in any
 *  HTML page that requires it.
 *
 *  Any+Time is a trademark of Andrew M. Andrews III.
 *
 ****************************************************************************/

//=============================================================================
//  AnyTime.utcLabel is an array of arrays, indexed by UTC offset IN MINUTES
//  (not hours-and-minutes).  This is used by AnyTime.Converter to display
//  time zone labels when the "%@" format specifier is used.  It is also used
//  by AnyTime.widget() to determine which time zone labels to offer as valid
//  choices when a user attempts to change the time zone.  NOTE: Positive
//  indicies are NOT signed.
//
//  Each sub-array contains a series of strings, each of which is a label
//  for a time-zone having the corresponding UTC offset.  The first string in
//  each sub-array is the default label for that UTC offset (the one used by
//  AnyTime.Converter.format() if utcFormatOffsetSubIndex is not specified and
//  setUtcFormatOffsetSubIndex() is not called.
//
//  To overcome a bug in Firefox 21 IRT negative array indicies, the initial
//  utcLabel must be defined as an object even though it is used as an array.
//=============================================================================

AnyTime.utcLabel = {};
AnyTime.utcLabel[-720]=[
  'BIT'
  ];
AnyTime.utcLabel[-660]=[
  'SST'
  ];
AnyTime.utcLabel[-600]=[
  'CKT'
  ,'HAST'
  ,'TAHT'
  ];
AnyTime.utcLabel[-540]=[
  'AKST'
  ,'GIT'
  ];
AnyTime.utcLabel[-510]=[
  'MIT'
  ];
AnyTime.utcLabel[-480]=[
  'CIST'
  ,'PST'
  ];
AnyTime.utcLabel[-420]=[
  'MST'
  ,'PDT'
  ];
AnyTime.utcLabel[-360]=[
  'CST'
  ,'EAST'
  ,'GALT'
  ,'MDT'
  ];
AnyTime.utcLabel[-300]=[
  'CDT'
  ,'COT'
  ,'ECT'
  ,'EST'
  ];
AnyTime.utcLabel[-240]=[
  'AST'
  ,'BOT'
  ,'CLT'
  ,'COST'
  ,'ECT'
  ,'EDT'
  ,'FKST'
  ,'GYT'
  ];
AnyTime.utcLabel[-210]=[
  'VET'
  ];
AnyTime.utcLabel[-180]=[
  'ART'
  ,'BRT'
  ,'CLST'
  ,'GFT'
  ,'UYT'
  ];
AnyTime.utcLabel[-150]=[
  'NT'
  ];
AnyTime.utcLabel[-120]=[
  'GST'
  ,'UYST'
  ];
AnyTime.utcLabel[-90]=[
  'NDT'
  ];
AnyTime.utcLabel[-60]=[
  'AZOST'
  ,'CVT'
  ];
AnyTime.utcLabel[0]=[
  'GMT'
  ,'WET'
  ];
AnyTime.utcLabel[60]=[
  'BST'
  ,'CET'
  ];
AnyTime.utcLabel[60]=[
  'WAT'
  ,'WEST'
  ];
AnyTime.utcLabel[120]=[
  'CAT'
  ,'CEST'
  ,'EET'
  ,'IST'
  ,'SAST'
  ];
AnyTime.utcLabel[180]=[
  'AST'
  ,'AST'
  ,'EAT'
  ,'EEST'
  ,'MSK'
  ];
AnyTime.utcLabel[210]=[
  'IRST'
  ];
AnyTime.utcLabel[240]=[
  'AMT'
  ,'AST'
  ,'AZT'
  ,'GET'
  ,'MUT'
  ,'RET'
  ,'SAMT'
  ,'SCT'
  ];
AnyTime.utcLabel[270]=[
  'AFT'
  ];
AnyTime.utcLabel[300]=[
  'AMST'
  ,'HMT'
  ,'PKT'
  ,'YEKT'
  ];
AnyTime.utcLabel[330]=[
  'IST'
  ,'SLT'
  ];
AnyTime.utcLabel[345]=[
  'NPT'
  ];
AnyTime.utcLabel[360]=[
  'BIOT'
  ,'BST'
  ,'BTT'
  ,'OMST'
  ];
AnyTime.utcLabel[390]=[
  'CCT'
  ,'MST'
  ];
AnyTime.utcLabel[420]=[
  'CXT'
  ,'KRAT'
  ,'THA'
  ];
AnyTime.utcLabel[480]=[
  'ACT'
  ,'AWST'
  ,'BDT'
  ,'CST'
  ,'HKT'
  ,'IRKT'
  ,'MST'
  ,'PST'
  ,'SST'
  ];
AnyTime.utcLabel[540]=[
  'JST'
  ,'KST'
  ,'YAKT'
  ];
AnyTime.utcLabel[570]=[
  'ACST'
  ];
AnyTime.utcLabel[600]=[
  'AEST'
  ,'ChST'
  ,'VLAT'
  ];
AnyTime.utcLabel[630]=[
  'LHST'
  ];
AnyTime.utcLabel[660]=[
  'MAGT'
  ,'SBT'
  ];
AnyTime.utcLabel[690]=[
  'NFT'
  ];
AnyTime.utcLabel[720]=[
  'FJT'
  ,'GILT'
  ,'PETT'
  ];
AnyTime.utcLabel[765]=[
  'CHAST'
  ];
AnyTime.utcLabel[780]=[
  'PHOT'
  ];
AnyTime.utcLabel[840]=[
  'LINT'
  ];

//
//END OF FILE
//