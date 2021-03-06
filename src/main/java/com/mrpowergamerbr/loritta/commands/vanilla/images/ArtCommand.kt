package com.mrpowergamerbr.loritta.commands.vanilla.images

import com.mrpowergamerbr.loritta.Loritta
import com.mrpowergamerbr.loritta.commands.AbstractCommand
import com.mrpowergamerbr.loritta.commands.CommandCategory
import com.mrpowergamerbr.loritta.commands.CommandContext
import com.mrpowergamerbr.loritta.utils.LorittaImage
import com.mrpowergamerbr.loritta.utils.LorittaUtils
import com.mrpowergamerbr.loritta.utils.locale.BaseLocale
import com.mrpowergamerbr.loritta.utils.toBufferedImage
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

class ArtCommand : AbstractCommand("art", listOf("arte"), category = CommandCategory.IMAGES) {

	override fun getDescription(locale: BaseLocale): String {
		return locale.get("ART_Description")
	}

	override fun getExample(): List<String> {
		return listOf("@Loritta");
	}

	override fun getUsage(): String {
		return "<imagem>";
	}

	override fun needsToUploadFiles(): Boolean {
		return true
	}

	override fun run(context: CommandContext, locale: BaseLocale) {
		var contextImage = LorittaUtils.getImageFromContext(context, 0)
		if (!LorittaUtils.isValidImage(context, contextImage)) {
			return
		}
		var template = ImageIO.read(File(Loritta.ASSETS + "art.png")) // Template
		var base = BufferedImage(400, 400, BufferedImage.TYPE_INT_ARGB)
		var scaled = contextImage.getScaledInstance(400, 400, BufferedImage.SCALE_SMOOTH).toBufferedImage()

		var transformed = LorittaImage(scaled)
		transformed.setCorners(75f, 215f,
				172f, 242f,
				106f, 399f,
				13f, 369f)

		base.graphics.drawImage(transformed.bufferedImage, 0, 0, null);
		base.graphics.drawImage(template, 0, 0, null)

		context.sendFile(base, "art.png", context.getAsMention(true));
	}
}