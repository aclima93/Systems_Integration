
package generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="technical_data" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="general_data" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="processor" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="operating_system" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="visualization_data" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="screen_type" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="screen_size_inches" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *                             &lt;element name="screen_size_centimeters" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *                             &lt;element name="screen_size_pixels_height" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                             &lt;element name="screen_size_pixels_width" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="comunication_data" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="networks" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="wifi" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="bluetooth" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="photography_data" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="camera_data" maxOccurs="unbounded">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="value" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *                                       &lt;element name="units" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="video_data" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="camera_data" maxOccurs="unbounded">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="quality" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *                                       &lt;element name="height" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                                       &lt;element name="width" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="multimedia_compatibility_data" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="read_formats_data" maxOccurs="unbounded">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="connections_data" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="connection_data" maxOccurs="unbounded">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="storage_data" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="internal_storage_size" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                             &lt;element name="internal_storage_units" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="memory_card_size" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                             &lt;element name="memory_card_units" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="memory_card_type" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="autonomy_data" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="batery_type" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="batery_value" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                             &lt;element name="batery_units" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="autonomy" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="misc_data" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="title" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *       &lt;attribute name="price">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}float">
 *             &lt;minInclusive value="0"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="currency" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="summary_data" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "technicalData"
})
@XmlRootElement(name = "smartphone")
public class Smartphone {

    @XmlElement(name = "technical_data")
    protected Smartphone.TechnicalData technicalData;
    @XmlAttribute(name = "price")
    protected Float price;
    @XmlAttribute(name = "name")
    protected String name;
    @XmlAttribute(name = "currency")
    protected String currency;
    @XmlAttribute(name = "summary_data")
    protected String summaryData;

    /**
     * Gets the value of the technicalData property.
     * 
     * @return
     *     possible object is
     *     {@link Smartphone.TechnicalData }
     *     
     */
    public Smartphone.TechnicalData getTechnicalData() {
        return technicalData;
    }

    /**
     * Sets the value of the technicalData property.
     * 
     * @param value
     *     allowed object is
     *     {@link Smartphone.TechnicalData }
     *     
     */
    public void setTechnicalData(Smartphone.TechnicalData value) {
        this.technicalData = value;
    }

    /**
     * Gets the value of the price property.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getPrice() {
        return price;
    }

    /**
     * Sets the value of the price property.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setPrice(Float value) {
        this.price = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the currency property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * Sets the value of the currency property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCurrency(String value) {
        this.currency = value;
    }

    /**
     * Gets the value of the summaryData property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSummaryData() {
        return summaryData;
    }

    /**
     * Sets the value of the summaryData property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSummaryData(String value) {
        this.summaryData = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="general_data" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="processor" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="operating_system" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="visualization_data" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="screen_type" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="screen_size_inches" type="{http://www.w3.org/2001/XMLSchema}float"/>
     *                   &lt;element name="screen_size_centimeters" type="{http://www.w3.org/2001/XMLSchema}float"/>
     *                   &lt;element name="screen_size_pixels_height" type="{http://www.w3.org/2001/XMLSchema}int"/>
     *                   &lt;element name="screen_size_pixels_width" type="{http://www.w3.org/2001/XMLSchema}int"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="comunication_data" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="networks" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="wifi" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="bluetooth" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="photography_data" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="camera_data" maxOccurs="unbounded">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="value" type="{http://www.w3.org/2001/XMLSchema}float"/>
     *                             &lt;element name="units" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                           &lt;/sequence>
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="video_data" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="camera_data" maxOccurs="unbounded">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="quality" type="{http://www.w3.org/2001/XMLSchema}float"/>
     *                             &lt;element name="height" type="{http://www.w3.org/2001/XMLSchema}int"/>
     *                             &lt;element name="width" type="{http://www.w3.org/2001/XMLSchema}int"/>
     *                           &lt;/sequence>
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="multimedia_compatibility_data" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="read_formats_data" maxOccurs="unbounded">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                           &lt;/sequence>
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="connections_data" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="connection_data" maxOccurs="unbounded">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                           &lt;/sequence>
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="storage_data" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="internal_storage_size" type="{http://www.w3.org/2001/XMLSchema}int"/>
     *                   &lt;element name="internal_storage_units" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="memory_card_size" type="{http://www.w3.org/2001/XMLSchema}int"/>
     *                   &lt;element name="memory_card_units" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="memory_card_type" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="autonomy_data" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="batery_type" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="batery_value" type="{http://www.w3.org/2001/XMLSchema}int"/>
     *                   &lt;element name="batery_units" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="autonomy" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="misc_data" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="title" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "generalData",
        "visualizationData",
        "comunicationData",
        "photographyData",
        "videoData",
        "multimediaCompatibilityData",
        "connectionsData",
        "storageData",
        "autonomyData",
        "miscData"
    })
    public static class TechnicalData {

        @XmlElement(name = "general_data")
        protected Smartphone.TechnicalData.GeneralData generalData;
        @XmlElement(name = "visualization_data")
        protected Smartphone.TechnicalData.VisualizationData visualizationData;
        @XmlElement(name = "comunication_data")
        protected Smartphone.TechnicalData.ComunicationData comunicationData;
        @XmlElement(name = "photography_data")
        protected Smartphone.TechnicalData.PhotographyData photographyData;
        @XmlElement(name = "video_data")
        protected Smartphone.TechnicalData.VideoData videoData;
        @XmlElement(name = "multimedia_compatibility_data")
        protected Smartphone.TechnicalData.MultimediaCompatibilityData multimediaCompatibilityData;
        @XmlElement(name = "connections_data")
        protected Smartphone.TechnicalData.ConnectionsData connectionsData;
        @XmlElement(name = "storage_data")
        protected Smartphone.TechnicalData.StorageData storageData;
        @XmlElement(name = "autonomy_data")
        protected Smartphone.TechnicalData.AutonomyData autonomyData;
        @XmlElement(name = "misc_data")
        protected Smartphone.TechnicalData.MiscData miscData;

        /**
         * Gets the value of the generalData property.
         * 
         * @return
         *     possible object is
         *     {@link Smartphone.TechnicalData.GeneralData }
         *     
         */
        public Smartphone.TechnicalData.GeneralData getGeneralData() {
            return generalData;
        }

        /**
         * Sets the value of the generalData property.
         * 
         * @param value
         *     allowed object is
         *     {@link Smartphone.TechnicalData.GeneralData }
         *     
         */
        public void setGeneralData(Smartphone.TechnicalData.GeneralData value) {
            this.generalData = value;
        }

        /**
         * Gets the value of the visualizationData property.
         * 
         * @return
         *     possible object is
         *     {@link Smartphone.TechnicalData.VisualizationData }
         *     
         */
        public Smartphone.TechnicalData.VisualizationData getVisualizationData() {
            return visualizationData;
        }

        /**
         * Sets the value of the visualizationData property.
         * 
         * @param value
         *     allowed object is
         *     {@link Smartphone.TechnicalData.VisualizationData }
         *     
         */
        public void setVisualizationData(Smartphone.TechnicalData.VisualizationData value) {
            this.visualizationData = value;
        }

        /**
         * Gets the value of the comunicationData property.
         * 
         * @return
         *     possible object is
         *     {@link Smartphone.TechnicalData.ComunicationData }
         *     
         */
        public Smartphone.TechnicalData.ComunicationData getComunicationData() {
            return comunicationData;
        }

        /**
         * Sets the value of the comunicationData property.
         * 
         * @param value
         *     allowed object is
         *     {@link Smartphone.TechnicalData.ComunicationData }
         *     
         */
        public void setComunicationData(Smartphone.TechnicalData.ComunicationData value) {
            this.comunicationData = value;
        }

        /**
         * Gets the value of the photographyData property.
         * 
         * @return
         *     possible object is
         *     {@link Smartphone.TechnicalData.PhotographyData }
         *     
         */
        public Smartphone.TechnicalData.PhotographyData getPhotographyData() {
            return photographyData;
        }

        /**
         * Sets the value of the photographyData property.
         * 
         * @param value
         *     allowed object is
         *     {@link Smartphone.TechnicalData.PhotographyData }
         *     
         */
        public void setPhotographyData(Smartphone.TechnicalData.PhotographyData value) {
            this.photographyData = value;
        }

        /**
         * Gets the value of the videoData property.
         * 
         * @return
         *     possible object is
         *     {@link Smartphone.TechnicalData.VideoData }
         *     
         */
        public Smartphone.TechnicalData.VideoData getVideoData() {
            return videoData;
        }

        /**
         * Sets the value of the videoData property.
         * 
         * @param value
         *     allowed object is
         *     {@link Smartphone.TechnicalData.VideoData }
         *     
         */
        public void setVideoData(Smartphone.TechnicalData.VideoData value) {
            this.videoData = value;
        }

        /**
         * Gets the value of the multimediaCompatibilityData property.
         * 
         * @return
         *     possible object is
         *     {@link Smartphone.TechnicalData.MultimediaCompatibilityData }
         *     
         */
        public Smartphone.TechnicalData.MultimediaCompatibilityData getMultimediaCompatibilityData() {
            return multimediaCompatibilityData;
        }

        /**
         * Sets the value of the multimediaCompatibilityData property.
         * 
         * @param value
         *     allowed object is
         *     {@link Smartphone.TechnicalData.MultimediaCompatibilityData }
         *     
         */
        public void setMultimediaCompatibilityData(Smartphone.TechnicalData.MultimediaCompatibilityData value) {
            this.multimediaCompatibilityData = value;
        }

        /**
         * Gets the value of the connectionsData property.
         * 
         * @return
         *     possible object is
         *     {@link Smartphone.TechnicalData.ConnectionsData }
         *     
         */
        public Smartphone.TechnicalData.ConnectionsData getConnectionsData() {
            return connectionsData;
        }

        /**
         * Sets the value of the connectionsData property.
         * 
         * @param value
         *     allowed object is
         *     {@link Smartphone.TechnicalData.ConnectionsData }
         *     
         */
        public void setConnectionsData(Smartphone.TechnicalData.ConnectionsData value) {
            this.connectionsData = value;
        }

        /**
         * Gets the value of the storageData property.
         * 
         * @return
         *     possible object is
         *     {@link Smartphone.TechnicalData.StorageData }
         *     
         */
        public Smartphone.TechnicalData.StorageData getStorageData() {
            return storageData;
        }

        /**
         * Sets the value of the storageData property.
         * 
         * @param value
         *     allowed object is
         *     {@link Smartphone.TechnicalData.StorageData }
         *     
         */
        public void setStorageData(Smartphone.TechnicalData.StorageData value) {
            this.storageData = value;
        }

        /**
         * Gets the value of the autonomyData property.
         * 
         * @return
         *     possible object is
         *     {@link Smartphone.TechnicalData.AutonomyData }
         *     
         */
        public Smartphone.TechnicalData.AutonomyData getAutonomyData() {
            return autonomyData;
        }

        /**
         * Sets the value of the autonomyData property.
         * 
         * @param value
         *     allowed object is
         *     {@link Smartphone.TechnicalData.AutonomyData }
         *     
         */
        public void setAutonomyData(Smartphone.TechnicalData.AutonomyData value) {
            this.autonomyData = value;
        }

        /**
         * Gets the value of the miscData property.
         * 
         * @return
         *     possible object is
         *     {@link Smartphone.TechnicalData.MiscData }
         *     
         */
        public Smartphone.TechnicalData.MiscData getMiscData() {
            return miscData;
        }

        /**
         * Sets the value of the miscData property.
         * 
         * @param value
         *     allowed object is
         *     {@link Smartphone.TechnicalData.MiscData }
         *     
         */
        public void setMiscData(Smartphone.TechnicalData.MiscData value) {
            this.miscData = value;
        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="batery_type" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="batery_value" type="{http://www.w3.org/2001/XMLSchema}int"/>
         *         &lt;element name="batery_units" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="autonomy" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *       &lt;/sequence>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "bateryType",
            "bateryValue",
            "bateryUnits",
            "autonomy"
        })
        public static class AutonomyData {

            @XmlElement(name = "batery_type", required = true)
            protected String bateryType;
            @XmlElement(name = "batery_value")
            protected int bateryValue;
            @XmlElement(name = "batery_units", required = true)
            protected String bateryUnits;
            @XmlElement(required = true)
            protected String autonomy;

            /**
             * Gets the value of the bateryType property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getBateryType() {
                return bateryType;
            }

            /**
             * Sets the value of the bateryType property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setBateryType(String value) {
                this.bateryType = value;
            }

            /**
             * Gets the value of the bateryValue property.
             * 
             */
            public int getBateryValue() {
                return bateryValue;
            }

            /**
             * Sets the value of the bateryValue property.
             * 
             */
            public void setBateryValue(int value) {
                this.bateryValue = value;
            }

            /**
             * Gets the value of the bateryUnits property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getBateryUnits() {
                return bateryUnits;
            }

            /**
             * Sets the value of the bateryUnits property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setBateryUnits(String value) {
                this.bateryUnits = value;
            }

            /**
             * Gets the value of the autonomy property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getAutonomy() {
                return autonomy;
            }

            /**
             * Sets the value of the autonomy property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setAutonomy(String value) {
                this.autonomy = value;
            }

        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="networks" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="wifi" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="bluetooth" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *       &lt;/sequence>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "networks",
            "wifi",
            "bluetooth"
        })
        public static class ComunicationData {

            @XmlElement(required = true)
            protected String networks;
            @XmlElement(required = true)
            protected String wifi;
            @XmlElement(required = true)
            protected String bluetooth;

            /**
             * Gets the value of the networks property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getNetworks() {
                return networks;
            }

            /**
             * Sets the value of the networks property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setNetworks(String value) {
                this.networks = value;
            }

            /**
             * Gets the value of the wifi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getWifi() {
                return wifi;
            }

            /**
             * Sets the value of the wifi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setWifi(String value) {
                this.wifi = value;
            }

            /**
             * Gets the value of the bluetooth property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getBluetooth() {
                return bluetooth;
            }

            /**
             * Sets the value of the bluetooth property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setBluetooth(String value) {
                this.bluetooth = value;
            }

        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="connection_data" maxOccurs="unbounded">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                 &lt;/sequence>
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *       &lt;/sequence>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "connectionData"
        })
        public static class ConnectionsData {

            @XmlElement(name = "connection_data", required = true)
            protected List<Smartphone.TechnicalData.ConnectionsData.ConnectionData> connectionData;

            /**
             * Gets the value of the connectionData property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the connectionData property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getConnectionData().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link Smartphone.TechnicalData.ConnectionsData.ConnectionData }
             * 
             * 
             */
            public List<Smartphone.TechnicalData.ConnectionsData.ConnectionData> getConnectionData() {
                if (connectionData == null) {
                    connectionData = new ArrayList<Smartphone.TechnicalData.ConnectionsData.ConnectionData>();
                }
                return this.connectionData;
            }


            /**
             * <p>Java class for anonymous complex type.
             * 
             * <p>The following schema fragment specifies the expected content contained within this class.
             * 
             * <pre>
             * &lt;complexType>
             *   &lt;complexContent>
             *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *       &lt;sequence>
             *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *       &lt;/sequence>
             *     &lt;/restriction>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                "name"
            })
            public static class ConnectionData {

                @XmlElement(required = true)
                protected String name;

                /**
                 * Gets the value of the name property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getName() {
                    return name;
                }

                /**
                 * Sets the value of the name property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setName(String value) {
                    this.name = value;
                }

            }

        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="processor" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="operating_system" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *       &lt;/sequence>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "processor",
            "operatingSystem"
        })
        public static class GeneralData {

            @XmlElement(required = true)
            protected String processor;
            @XmlElement(name = "operating_system", required = true)
            protected String operatingSystem;

            /**
             * Gets the value of the processor property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getProcessor() {
                return processor;
            }

            /**
             * Sets the value of the processor property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setProcessor(String value) {
                this.processor = value;
            }

            /**
             * Gets the value of the operatingSystem property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getOperatingSystem() {
                return operatingSystem;
            }

            /**
             * Sets the value of the operatingSystem property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setOperatingSystem(String value) {
                this.operatingSystem = value;
            }

        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="title" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *       &lt;/sequence>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "title",
            "description"
        })
        public static class MiscData {

            @XmlElement(required = true)
            protected String title;
            @XmlElement(required = true)
            protected String description;

            /**
             * Gets the value of the title property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getTitle() {
                return title;
            }

            /**
             * Sets the value of the title property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setTitle(String value) {
                this.title = value;
            }

            /**
             * Gets the value of the description property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDescription() {
                return description;
            }

            /**
             * Sets the value of the description property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDescription(String value) {
                this.description = value;
            }

        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="read_formats_data" maxOccurs="unbounded">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                 &lt;/sequence>
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *       &lt;/sequence>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "readFormatsData"
        })
        public static class MultimediaCompatibilityData {

            @XmlElement(name = "read_formats_data", required = true)
            protected List<Smartphone.TechnicalData.MultimediaCompatibilityData.ReadFormatsData> readFormatsData;

            /**
             * Gets the value of the readFormatsData property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the readFormatsData property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getReadFormatsData().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link Smartphone.TechnicalData.MultimediaCompatibilityData.ReadFormatsData }
             * 
             * 
             */
            public List<Smartphone.TechnicalData.MultimediaCompatibilityData.ReadFormatsData> getReadFormatsData() {
                if (readFormatsData == null) {
                    readFormatsData = new ArrayList<Smartphone.TechnicalData.MultimediaCompatibilityData.ReadFormatsData>();
                }
                return this.readFormatsData;
            }


            /**
             * <p>Java class for anonymous complex type.
             * 
             * <p>The following schema fragment specifies the expected content contained within this class.
             * 
             * <pre>
             * &lt;complexType>
             *   &lt;complexContent>
             *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *       &lt;sequence>
             *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *       &lt;/sequence>
             *     &lt;/restriction>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                "name"
            })
            public static class ReadFormatsData {

                @XmlElement(required = true)
                protected String name;

                /**
                 * Gets the value of the name property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getName() {
                    return name;
                }

                /**
                 * Sets the value of the name property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setName(String value) {
                    this.name = value;
                }

            }

        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="camera_data" maxOccurs="unbounded">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="value" type="{http://www.w3.org/2001/XMLSchema}float"/>
         *                   &lt;element name="units" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                 &lt;/sequence>
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *       &lt;/sequence>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "cameraData"
        })
        public static class PhotographyData {

            @XmlElement(name = "camera_data", required = true)
            protected List<Smartphone.TechnicalData.PhotographyData.CameraData> cameraData;

            /**
             * Gets the value of the cameraData property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the cameraData property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getCameraData().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link Smartphone.TechnicalData.PhotographyData.CameraData }
             * 
             * 
             */
            public List<Smartphone.TechnicalData.PhotographyData.CameraData> getCameraData() {
                if (cameraData == null) {
                    cameraData = new ArrayList<Smartphone.TechnicalData.PhotographyData.CameraData>();
                }
                return this.cameraData;
            }


            /**
             * <p>Java class for anonymous complex type.
             * 
             * <p>The following schema fragment specifies the expected content contained within this class.
             * 
             * <pre>
             * &lt;complexType>
             *   &lt;complexContent>
             *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *       &lt;sequence>
             *         &lt;element name="value" type="{http://www.w3.org/2001/XMLSchema}float"/>
             *         &lt;element name="units" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *       &lt;/sequence>
             *     &lt;/restriction>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                "value",
                "units"
            })
            public static class CameraData {

                protected float value;
                @XmlElement(required = true)
                protected String units;

                /**
                 * Gets the value of the value property.
                 * 
                 */
                public float getValue() {
                    return value;
                }

                /**
                 * Sets the value of the value property.
                 * 
                 */
                public void setValue(float value) {
                    this.value = value;
                }

                /**
                 * Gets the value of the units property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getUnits() {
                    return units;
                }

                /**
                 * Sets the value of the units property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setUnits(String value) {
                    this.units = value;
                }

            }

        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="internal_storage_size" type="{http://www.w3.org/2001/XMLSchema}int"/>
         *         &lt;element name="internal_storage_units" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="memory_card_size" type="{http://www.w3.org/2001/XMLSchema}int"/>
         *         &lt;element name="memory_card_units" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="memory_card_type" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *       &lt;/sequence>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "internalStorageSize",
            "internalStorageUnits",
            "memoryCardSize",
            "memoryCardUnits",
            "memoryCardType"
        })
        public static class StorageData {

            @XmlElement(name = "internal_storage_size")
            protected int internalStorageSize;
            @XmlElement(name = "internal_storage_units", required = true)
            protected String internalStorageUnits;
            @XmlElement(name = "memory_card_size")
            protected int memoryCardSize;
            @XmlElement(name = "memory_card_units", required = true)
            protected String memoryCardUnits;
            @XmlElement(name = "memory_card_type", required = true)
            protected String memoryCardType;

            /**
             * Gets the value of the internalStorageSize property.
             * 
             */
            public int getInternalStorageSize() {
                return internalStorageSize;
            }

            /**
             * Sets the value of the internalStorageSize property.
             * 
             */
            public void setInternalStorageSize(int value) {
                this.internalStorageSize = value;
            }

            /**
             * Gets the value of the internalStorageUnits property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getInternalStorageUnits() {
                return internalStorageUnits;
            }

            /**
             * Sets the value of the internalStorageUnits property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setInternalStorageUnits(String value) {
                this.internalStorageUnits = value;
            }

            /**
             * Gets the value of the memoryCardSize property.
             * 
             */
            public int getMemoryCardSize() {
                return memoryCardSize;
            }

            /**
             * Sets the value of the memoryCardSize property.
             * 
             */
            public void setMemoryCardSize(int value) {
                this.memoryCardSize = value;
            }

            /**
             * Gets the value of the memoryCardUnits property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getMemoryCardUnits() {
                return memoryCardUnits;
            }

            /**
             * Sets the value of the memoryCardUnits property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setMemoryCardUnits(String value) {
                this.memoryCardUnits = value;
            }

            /**
             * Gets the value of the memoryCardType property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getMemoryCardType() {
                return memoryCardType;
            }

            /**
             * Sets the value of the memoryCardType property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setMemoryCardType(String value) {
                this.memoryCardType = value;
            }

        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="camera_data" maxOccurs="unbounded">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="quality" type="{http://www.w3.org/2001/XMLSchema}float"/>
         *                   &lt;element name="height" type="{http://www.w3.org/2001/XMLSchema}int"/>
         *                   &lt;element name="width" type="{http://www.w3.org/2001/XMLSchema}int"/>
         *                 &lt;/sequence>
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *       &lt;/sequence>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "cameraData"
        })
        public static class VideoData {

            @XmlElement(name = "camera_data", required = true)
            protected List<Smartphone.TechnicalData.VideoData.CameraData> cameraData;

            /**
             * Gets the value of the cameraData property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the cameraData property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getCameraData().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link Smartphone.TechnicalData.VideoData.CameraData }
             * 
             * 
             */
            public List<Smartphone.TechnicalData.VideoData.CameraData> getCameraData() {
                if (cameraData == null) {
                    cameraData = new ArrayList<Smartphone.TechnicalData.VideoData.CameraData>();
                }
                return this.cameraData;
            }


            /**
             * <p>Java class for anonymous complex type.
             * 
             * <p>The following schema fragment specifies the expected content contained within this class.
             * 
             * <pre>
             * &lt;complexType>
             *   &lt;complexContent>
             *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *       &lt;sequence>
             *         &lt;element name="quality" type="{http://www.w3.org/2001/XMLSchema}float"/>
             *         &lt;element name="height" type="{http://www.w3.org/2001/XMLSchema}int"/>
             *         &lt;element name="width" type="{http://www.w3.org/2001/XMLSchema}int"/>
             *       &lt;/sequence>
             *     &lt;/restriction>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                "quality",
                "height",
                "width"
            })
            public static class CameraData {

                protected float quality;
                protected int height;
                protected int width;

                /**
                 * Gets the value of the quality property.
                 * 
                 */
                public float getQuality() {
                    return quality;
                }

                /**
                 * Sets the value of the quality property.
                 * 
                 */
                public void setQuality(float value) {
                    this.quality = value;
                }

                /**
                 * Gets the value of the height property.
                 * 
                 */
                public int getHeight() {
                    return height;
                }

                /**
                 * Sets the value of the height property.
                 * 
                 */
                public void setHeight(int value) {
                    this.height = value;
                }

                /**
                 * Gets the value of the width property.
                 * 
                 */
                public int getWidth() {
                    return width;
                }

                /**
                 * Sets the value of the width property.
                 * 
                 */
                public void setWidth(int value) {
                    this.width = value;
                }

            }

        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="screen_type" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="screen_size_inches" type="{http://www.w3.org/2001/XMLSchema}float"/>
         *         &lt;element name="screen_size_centimeters" type="{http://www.w3.org/2001/XMLSchema}float"/>
         *         &lt;element name="screen_size_pixels_height" type="{http://www.w3.org/2001/XMLSchema}int"/>
         *         &lt;element name="screen_size_pixels_width" type="{http://www.w3.org/2001/XMLSchema}int"/>
         *       &lt;/sequence>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "screenType",
            "screenSizeInches",
            "screenSizeCentimeters",
            "screenSizePixelsHeight",
            "screenSizePixelsWidth"
        })
        public static class VisualizationData {

            @XmlElement(name = "screen_type", required = true)
            protected String screenType;
            @XmlElement(name = "screen_size_inches")
            protected float screenSizeInches;
            @XmlElement(name = "screen_size_centimeters")
            protected float screenSizeCentimeters;
            @XmlElement(name = "screen_size_pixels_height")
            protected int screenSizePixelsHeight;
            @XmlElement(name = "screen_size_pixels_width")
            protected int screenSizePixelsWidth;

            /**
             * Gets the value of the screenType property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getScreenType() {
                return screenType;
            }

            /**
             * Sets the value of the screenType property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setScreenType(String value) {
                this.screenType = value;
            }

            /**
             * Gets the value of the screenSizeInches property.
             * 
             */
            public float getScreenSizeInches() {
                return screenSizeInches;
            }

            /**
             * Sets the value of the screenSizeInches property.
             * 
             */
            public void setScreenSizeInches(float value) {
                this.screenSizeInches = value;
            }

            /**
             * Gets the value of the screenSizeCentimeters property.
             * 
             */
            public float getScreenSizeCentimeters() {
                return screenSizeCentimeters;
            }

            /**
             * Sets the value of the screenSizeCentimeters property.
             * 
             */
            public void setScreenSizeCentimeters(float value) {
                this.screenSizeCentimeters = value;
            }

            /**
             * Gets the value of the screenSizePixelsHeight property.
             * 
             */
            public int getScreenSizePixelsHeight() {
                return screenSizePixelsHeight;
            }

            /**
             * Sets the value of the screenSizePixelsHeight property.
             * 
             */
            public void setScreenSizePixelsHeight(int value) {
                this.screenSizePixelsHeight = value;
            }

            /**
             * Gets the value of the screenSizePixelsWidth property.
             * 
             */
            public int getScreenSizePixelsWidth() {
                return screenSizePixelsWidth;
            }

            /**
             * Sets the value of the screenSizePixelsWidth property.
             * 
             */
            public void setScreenSizePixelsWidth(int value) {
                this.screenSizePixelsWidth = value;
            }

        }

    }

}
