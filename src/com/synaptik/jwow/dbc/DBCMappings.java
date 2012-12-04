package com.synaptik.jwow.dbc;

import java.util.HashMap;
import java.util.Map;

/**
 * Just an idea. Not sure I'll ever implement this...
 */
public final class DBCMappings {
	public DBCMapping EMOTESTEXT = new DBCMapping("{id}{sRefCon}{animationID}{emotesTextData3rdPerson}{emotesTextData2ndPerson}{emotesTextData1stPerson}{}{emotesTextData2ndPersonNoTarget}"); 
	public DBCMapping EMOTESTEXTDATA = new DBCMapping("{id}{sRefName}"); 
	public DBCMapping STARTUPSTRINGS = new DBCMapping("{id}{name}{text}");
	
	public static Map<String,DBCMapping> MAPPINGS = new HashMap<String,DBCMapping>();
	
	static {
		MAPPINGS.put("areaassignment.dbc", new DBCMapping("{id}{iRefID_mapID}{iRefID_areaID}{chunkX}{chunkY}"));
		MAPPINGS.put("chatprofanity.dbc", new DBCMapping("{id}{dirty_name:String}{languageID}"));
		MAPPINGS.put("charsections.dbc", new DBCMapping("{id}{iRefID_race}{gender}{generalType}{texture1:String}{texture2:String}{texture3:String}{flags}{type}{variation}"));
		MAPPINGS.put("creaturedisplayinfoextra.dbc", new DBCMapping("{id}{race}{creatureType}{gender}{skinColor}{faceType}{hairType}{hairStyle}{beardStyle}{helm}{shoulder}{shirt}{curiass}{belt}{lets}{boots}{rings}{gloves}{wrist}{cape}{canEquip}{texture:String}"));
		MAPPINGS.put("emotes.dbc", new DBCMapping("{id}{command:String}{animationData}{emoteFlags}{specProc}{specProcParam}{soundEntries}"));
		MAPPINGS.put("emotestext.dbc", new DBCMapping("{id}{refCon}{emotes}{textData:Integer[16]}"));
		MAPPINGS.put("gameobjectdisplayinfo.dbc", new DBCMapping("{id}{filename:String}{standID}{openID}{loopID}{closeID}{destroyID}{openedID}{custom:Integer[4]}{vectors:Float[5]}"));
		MAPPINGS.put("groundeffecttexture.dbc", new DBCMapping("{id}{doodads:Integer[4]}{weight:Integer[4]}{coverage}{type}"));
		MAPPINGS.put("filedata.dbc", new DBCMapping("{id}{filename:String}{filepath:String}"));
		MAPPINGS.put("loadingscreens.dbc", new DBCMapping("{id}{name:String}{path:String}{wideScreen}"));
		MAPPINGS.put("namegen.dbc", new DBCMapping("{id}{name:String}{raceID}{gender}"));
		MAPPINGS.put("namesprofanity.dbc", new DBCMapping("{id}{pattern:String}{languageID}"));
		MAPPINGS.put("namesreserved.dbc", new DBCMapping("{id}{pattern:String}{languageID}"));
		MAPPINGS.put("questxp.dbc", new DBCMapping("{id}"));
		MAPPINGS.put("soundentries.dbc", new DBCMapping("{id}{type}{name:String}{filenames:String[10]}{freq:String[10]}{filepath:String}{volume:Float}{flags}{minDist:Float}{distCutoff:Float}{EAXDef}{soundEntriesID}"));
		MAPPINGS.put("spellvisualeffectname.dbc", new DBCMapping("{id}{name:String}{filename:String}{size:Float}{scale:Float}{minScale:Float}{maxScale:Float}"));
		MAPPINGS.put("spellvisualkit.dbc", new DBCMapping("{id}{startAnimID}{animID}{animKitID}{headEffect}{chestEffect}{baseEffect}{leftHandEffect}{rightHandEffect}{breathEffect}{leftWeaponEffect}{rightWeaponEffect}{specialEffect}{worldEffect}{soundID}{shakeID}{charProc}{params:Integer[16]}{flags}"));
		MAPPINGS.put("taxipathnode.dbc", new DBCMapping("{id}{pathID}{nodeIndex}{map}{point:Float[3]}{flags}{delay}{arrivalID}{departureID}"));
		MAPPINGS.put("terraintype.dbc", new DBCMapping("{id}{description:String}{footstepRun}{footstepWalk}{sound}{flags}"));
		MAPPINGS.put("weather.dbc", new DBCMapping("{id}{ambienceID}{effectType}{skybox}{effectColor:Double[3]}{effectTexture:String}"));
		MAPPINGS.put("worldmaparea.dbc", new DBCMapping("{id}{mapID}{areaTableID}{name:String}{bounding:Float[4]}{flags}{displayMapID}{dungeonFloorID}{parentWorldMapID}"));
		MAPPINGS.put("worldmapcontinent.dbc", new DBCMapping("{id}{mapID}{boundary:Integer[4]}{offset:Float[2]}{scale:Float}{taxiBox:Float[4]}{worldMapID}"));
		MAPPINGS.put("worldmapoverlay.dbc", new DBCMapping("{id}{iRefID_mapAreaID}{iRefID_areaID}{mapPointX}{mapPointY}{textureName:String}{textureWidth}{textureHeight}{offsetX}{offsetY}{hitRectTop}{hitRectLeft}{hitRectBottom}{hitRectRight}"));
		MAPPINGS.put("zoneintromusictable.dbc", new DBCMapping("{id}{name:String}{iRefID_soundEntries}{priority}{minDelayMinutes}"));
		MAPPINGS.put("zonelight.dbc", new DBCMapping("{id}"));
		MAPPINGS.put("zonelightpoints.dbc", new DBCMapping("{id}"));
		MAPPINGS.put("zonemusic.dbc", new DBCMapping("{id}{name:String}{silenceIntervalMinDay}{silenceIntervalMinNight}{silenceIntervalMaxDay}{silenceIntervalMaxNight}{dayMusic}{nightMusic}"));
	}
}
