package dbconnect.xml.converters;

/**
 * Does the work of copying between DAO and regular objects
 * @author mstrobel
 *
 * @param <T> The type of the regular object that can be produced.
 * @param <D> The type of the DAO that can be produced.
 */
public interface IConverter<T, D> {
		
	/**
	 * Converts a DAO into a regular instance.
	 * @param other the DAO.
	 * @return A regular instance which has matching properties to the DAO.
	 */
	public T convert(D other);
	
	/**
	 * Converts a regular instance into a DAO.
	 * @param other The regular instance of the object
	 * @return A DAO that which has matching properties to the regular instance.
	 */
	public D unconvert(T other);
}
