package crc.util;

import java.util.zip.CRC32;

import org.apache.log4j.Logger;

public class CRCUtil
{
	static Logger logger = Logger.getLogger(CRCUtil.class);

	public static boolean checkCRC32(long crcCode, byte[] bin)
	{

		CRC32 crc = new CRC32();
		crc.update(bin);
		long result = crc.getValue();
		logger.info("the Correct CRC is:" + result);
		return result == crcCode;
	}
}
