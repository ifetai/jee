package de.java2enterprise.onlineshop.ejb;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.imageio.ImageIO;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.servlet.http.Part;

import de.java2enterprise.onlineshop.model.Customer;
import de.java2enterprise.onlineshop.model.Item;

/**
 * Session Bean implementation class SellBean
 */
@Stateless
public class SellBean implements SellBeanRemote, SellBeanLocal {

	public final static int MAX_IMAGE_LENGHT = 400;

	@PersistenceContext
	private EntityManager em;

	@Override
	public Item persist(Item item, Part part) {

		try {

			InputStream input = part.getInputStream();
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			byte[] buffer = new byte[10240];
			for (int length = 0; (length = input.read(buffer)) > 0;) {
				output.write(buffer, 0, length);
			}

			item.setFoto(scale(output.toByteArray()));
			em.persist(item);

		} catch (Exception e) {
			throw (EJBException) new EJBException(e).initCause(e);
		}
		return item;

	}

	@Override
	public List<Item> allMySellingItems(Item item, Customer customer) {

		List<Item> items = new ArrayList<>();

		try {
			TypedQuery<Item> query = em
					.createQuery("SELECT i FROM Item i " + "WHERE i.stock > 0 " + "AND i.seller= :seller", Item.class);
			query.setParameter("seller", customer);
			items = query.getResultList();

		} catch (Exception e) {
			throw (EJBException) new EJBException(e).initCause(e);
		}

		return items;

	}

	@Override
	public byte[] getPhoto(long id) {

		byte[] result;

		try {

			Query query = em.createQuery("select i.foto " + "from Item i " + "where i.id = :id");
			query.setParameter("id", id);
			result = (byte[]) query.getSingleResult();
		} catch (Exception e) {
			throw (EJBException) new EJBException(e).initCause(e);

		}

		return result;

	}
	
	
	@Override
	public Item persistModification(Item item, Part part) {
		try {
			if (part != null) {
				InputStream input = part.getInputStream();
				ByteArrayOutputStream output = new ByteArrayOutputStream();
				byte[] buffer = new byte[10240];
				for (int length = 0; (length = input.read(buffer)) > 0;) {
					output.write(buffer, 0, length);
				}
				
				item.setFoto(scale(output.toByteArray()));			
			}
			
			em.merge(item);
		} catch (Exception e) {
		
		
			throw (EJBException) new EJBException(e).initCause(e);
		}
		return item;

	}


	@Override
	public void terminateSale(Item item) {
		try {
			item.setStock(0);
			em.merge(item);
		} catch (Exception e) {
			throw (EJBException) new EJBException(e).initCause(e);
		}

	}
	

	public byte[] scale(byte[] foto) throws IOException {
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(foto);
		BufferedImage originalBufferedImage = ImageIO.read(byteArrayInputStream);

		double originalWidth = (double) originalBufferedImage.getWidth();
		double originalHeight = (double) originalBufferedImage.getHeight();

		double relevantLenght = originalWidth > originalHeight ? originalWidth : originalHeight;

		double transformationScale = MAX_IMAGE_LENGHT / relevantLenght;
		int width = (int) Math.round(transformationScale * originalWidth);
		int height = (int) Math.round(transformationScale * originalHeight);

		BufferedImage resizeBufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		Graphics2D g2d = resizeBufferedImage.createGraphics();
		g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);

		AffineTransform affineTransform = AffineTransform.getScaleInstance(transformationScale, transformationScale);
		g2d.drawRenderedImage(originalBufferedImage, affineTransform);

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(resizeBufferedImage, "PNG", baos);
		return baos.toByteArray();

	}


}
