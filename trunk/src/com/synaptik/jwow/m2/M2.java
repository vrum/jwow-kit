package com.synaptik.jwow.m2;

import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import com.synaptik.jwow.ByteBufferUtil;

/**
Reference: http://madx.dk/wowdev/wiki/index.php?title=M2/WotLK
@author Dan Watling <dan@synaptik.com>
 */
public class M2 {
	_offsets offsets;
	_other other;
	_data data;
	_lengths lengths;
	
	private M2() {
	}
	
	public static M2 read(ByteBuffer bb) {
		bb.order(ByteOrder.LITTLE_ENDIAN);	// format is in little endian, ensure the buffer is too
		M2 result = new M2();
		
		bb.get(result.other.magic);
		result.other.version = bb.getInt();
		result.lengths.lName = bb.getInt();
		result.offsets.ofsName = bb.getInt();
		result.other.globalModelFlags = bb.getInt();
		result.lengths.nGlobalSequences = bb.getInt();
		result.offsets.ofsGlobalSequences = bb.getInt();
		result.lengths.nAnimations = bb.getInt();
		result.offsets.ofsAnimations = bb.getInt();
		result.lengths.nAnimationLookup = bb.getInt();
		result.offsets.ofsAnimationLookup = bb.getInt();
		result.lengths.nBones = bb.getInt();
		result.offsets.ofsBones = bb.getInt();
		result.lengths.nKeyBoneLookup = bb.getInt();
		result.offsets.ofsKeyBoneLookup = bb.getInt();
		result.lengths.nVertices = bb.getInt();
		result.offsets.ofsVertices = bb.getInt();
		result.lengths.nViews = bb.getInt();
		result.lengths.nColors = bb.getInt();
		result.offsets.ofsColors = bb.getInt();
		result.lengths.nTextures = bb.getInt();
		result.offsets.ofsTextures = bb.getInt();
		result.lengths.nTransparency = bb.getInt();
		result.offsets.ofsTransparency = bb.getInt();
		result.lengths.nTextureAnimations = bb.getInt();
		result.offsets.ofsTextureAnimations = bb.getInt();
		result.lengths.nTexReplace = bb.getInt();
		result.offsets.ofsTexReplace = bb.getInt();
		result.lengths.nRenderFlags = bb.getInt();
		result.offsets.ofsRenderFlags = bb.getInt();
		result.lengths.nBoneLookupTable = bb.getInt();
		result.offsets.ofsBoneLookupTable = bb.getInt();
		result.lengths.nTexLookup = bb.getInt();
		result.offsets.ofsTexLookup = bb.getInt();
		result.lengths.nTexUnits = bb.getInt();
		result.offsets.ofsTexUnits = bb.getInt();
		result.lengths.nTransLookup = bb.getInt();
		result.offsets.ofsTransLookup = bb.getInt();
		result.lengths.nTexAnimLookup = bb.getInt();
		for (int index = 0; index < result.other.theFloats.length; index ++) {
			result.other.theFloats[index] = bb.getFloat();
		}
		result.lengths.nBoundingTringles = bb.getInt();
		result.offsets.ofsBoundTriangles = bb.getInt();
		result.lengths.nBoundingVertices = bb.getInt();
		result.offsets.ofsBoundingVertices = bb.getInt();
		result.lengths.nBoundingNormals = bb.getInt();
		result.offsets.ofsBoundingNormals = bb.getInt();
		result.lengths.nAttachments = bb.getInt();
		result.offsets.ofsAttachments = bb.getInt();
		result.lengths.nAttachLookup = bb.getInt();
		result.offsets.ofsAttachLookup = bb.getInt();
		result.lengths.nAttachments_2 = bb.getInt();
		result.offsets.ofsAttachments_2 = bb.getInt();
		result.lengths.nLights = bb.getInt();
		result.offsets.ofsLights = bb.getInt();
		result.lengths.nCameras = bb.getInt();
		result.offsets.ofsCameras = bb.getInt();
		result.lengths.nCameraLookup = bb.getInt();
		result.offsets.ofsCameraLookup = bb.getInt();
		result.lengths.nRibbonEmitters = bb.getInt();
		result.offsets.ofsRibbonEmitters = bb.getInt();
		result.lengths.nParticleEmitters = bb.getInt();
		result.offsets.ofsParticleEmitters = bb.getInt();
		result.lengths.nUnknown = bb.getInt();
		result.offsets.ofsUnknown = bb.getInt();
		
		readData(result, bb);
		
		return result;
	}
	
	public static M2 read(File f) throws Exception {
		FileInputStream fis = null;
		ByteBuffer bb = ByteBuffer.allocate((int)f.length());
		M2 result = null;
		try {
			fis = new FileInputStream(f);
			fis.getChannel().read(bb);
			bb.rewind();
			result = read(bb);
		} finally {
			fis.close();
		}
		return result;
	}
	
	protected static void readData(M2 result, ByteBuffer bb) {
		bb.position(result.offsets.ofsName);
		result.data.name = ByteBufferUtil.readString(bb, result.lengths.lName);
		
		bb.position(result.offsets.ofsGlobalSequences);
		result.data.globalSequences = ByteBufferUtil.readInts(bb, result.lengths.nGlobalSequences);
		
		bb.position(result.offsets.ofsAnimationLookup);
		result.data.animationLookups = ByteBufferUtil.readShorts(bb, result.lengths.nAnimationLookup);
		
		bb.position(result.offsets.ofsRenderFlags);
		result.data.renderFlags = ByteBufferUtil.readInts(bb, result.lengths.nRenderFlags);
		
		bb.position(result.offsets.ofsKeyBoneLookup);
		result.data.keyBoneLookupTable = ByteBufferUtil.readShorts(bb, result.lengths.nKeyBoneLookup);
		
		bb.position(result.offsets.ofsBoneLookupTable);
		result.data.boneLookupTable = ByteBufferUtil.readShorts(bb, result.lengths.nBoneLookupTable);
		
		bb.position(result.offsets.ofsVertices);
		result.data.vertexes = new Vertex[result.lengths.nVertices];
		for (int index = 0; index < result.lengths.nVertices; index ++) {
			result.data.vertexes[index] = Vertex.read(bb);
		}
		
		bb.position(result.offsets.ofsAnimations);
		result.data.animations = new AnimationSequence[result.lengths.nAnimations];
		for (int index = 0; index < result.lengths.nAnimations; index ++) {
			result.data.animations[index] = AnimationSequence.read(bb);
		}
	}
	
	class _offsets {
		int ofsName;
		int ofsGlobalSequences;
		int ofsAnimations;
		int ofsAnimationLookup;
		int ofsBones;
		int ofsKeyBoneLookup;
		int ofsVertices;
		int ofsColors;
		int ofsTextures;
		int ofsTransparency;
		int ofsTextureAnimations;
		int ofsTexReplace;
		int ofsRenderFlags;
		int ofsBoneLookupTable;
		int ofsTexLookup;
		int ofsTexUnits;
		int ofsBoundTriangles;
		int ofsBoundingVertices;
		int ofsBoundingNormals;
		int ofsAttachments;
		int ofsAttachLookup;
		int ofsAttachments_2;
		int ofsLights;
		int ofsCameras;
		int ofsCameraLookup;
		int ofsRibbonEmitters;
		int ofsParticleEmitters;
		int ofsUnknown;
		int ofsTransLookup;
	}
	
	public class _lengths {
		int lName;
		int nGlobalSequences;
		int nAnimations;
		int nAnimationLookup;
		int nBones;
		int nKeyBoneLookup;
		int nVertices;
		int nViews;
		int nColors;
		int nTextures;
		int nTransparency;
		int nTextureAnimations;
		int nTexReplace;
		int nRenderFlags;
		int nBoneLookupTable;
		int nTexLookup;
		int nTexUnits;
		int nTransLookup;
		int nTexAnimLookup;
		int nBoundingTringles;
		int nBoundingVertices;
		int nBoundingNormals;
		int nAttachments;
		int nAttachLookup;
		int nAttachments_2;
		int nLights;
		int nCameras;
		int nCameraLookup;
		int nRibbonEmitters;
		int nParticleEmitters;
		int nUnknown;
	}
	
	class _other {
		byte[] magic = new byte[4];
		int version;
		int globalModelFlags;
		float[] theFloats = new float[14];
	}
	
	class _data {
		String name = null;
		Vertex[] vertexes = null;
		int[] globalSequences;
		short[] animationLookups;
		int[] renderFlags;
		short[] boneLookupTable;
		short[] keyBoneLookupTable;
		AnimationSequence[] animations;
	}
}
