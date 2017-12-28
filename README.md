**Hajautetut ohjelmistojärjestelmät ja pilvipalvelut 2017 HARJOITUSTYÖ2

**KARTANKATSELUOHJELMA Tehtävänanto:

Työn tarkoituksena on demonstroida pavelupyyntöjen lähettämistä palvelimelle verkon yli. 

Tehtävänä on tehdä sovellus, jolla voi katsella WMS-palvelusta haettuja karttakuvia. WMS (Web
Map Service) on geografisten karttakuvien esittämiseen tarkoitettu standardiprotokolla. Palvelin
generoi tietokantansa perusteella asiakkaan antamia parametreja vastaavan karttakuvan.
WMS:ssä on kaksi pääasiallista kyselyä: getCapabilities-kysely ja getMap-kysely. Vastauksena
ensin mainittuun kyselyyn palvelin palauttaa XML-muodossa tietoja karttapalvelun tarjoamista
kerroksista, mm. palvelun tukemat koordinaatistot, karttakerrosten nimet ja niiden
reunakoordinaatit. getMap-kysely puolestaan hakee palvelimelta annettujen parametrien perusteella
karttakuvan, joka on normaali kuvatiedosto, esim. PNG tai JPG.

Seuraavassa on esimerkki getCapabilities-kyselystä:
http://demo.mapserver.org/cgi-bin/wms?SERVICE=WMS&VERSION=1.1.1
&REQUEST=GetCapabilities

Vastaavasti getMap-kyselyn muoto on seuraava:
http://demo.mapserver.org/cgi-bin/wms?SERVICE=WMS&VERSION=1.1.1
&REQUEST=GetMap&BBOX=-180,-90,180,90&SRS=EPSG:4326
&WIDTH=953&HEIGHT=480&LAYERS=bluemarble,country_bounds,continents,cities
&STYLES=&FORMAT=image/png&TRANSPARENT=true

Tässä kyselyssä parametreina ovat palvelun ilmaiseva vakioparametri WMS, WMS-versio, kyselyn
tyyppi, näytettävän alueen rajat (xmin, ymin, xmax, ymax), käytettävä koordinaatisto (tässä
harjoitustyössä käytetään EPSG:4326 eli WGS84-koordinaatistoa), karttakuvan leveys ja korkeus
kuvapisteinä, näytettävät kerrokset (useat kerrokset erotetaan toisistaan pilkulla), kartan piirtotyyliin
liittyvä parametri (jätetään tyhjäksi tässä työssä), kuvaformaatti (tässä png) ja taustan läpinäkyvyys
totuusarvona. Sekä getCapabilities- että getMap-kyselyä voi kokeilla normaalilla Internetselaimella.

Harjoitustyössä tehtävän sovelluksen tulisi toimia seuraavasti:

• Haetaan aluksi XML-muotoiset tiedot WMS-karttapalvelussa tarjotuista karttakerroksista
edellisessä esimerkissä esitetyllä getCapabilities-kyselyllä.
• Poimitaan palvelimen palauttamasta XML-datasta tiedot kaikkien karttakerrosten (layerstagien
sisällä) getMap-kyselyissä käytettävistä nimistä (name-tagit) sekä sovelluksessa
käyttäjille näkyvistä nimistä (title-tagit). XML-datassa siis on esimerkiksi continentsniminen
kerros, jonka käyttäjälle näkyvä nimi on World continents. XML-datan
jäsentämiseen tulee käyttää Javan valmiita kirjastoja (esim. paketit javax.xml.parsers ja
org.xml.sax).
• Sovellukselle on valmiiksi toteutettu käyttöliittymän runko (saatavissa kurssin Moodle-
sivulta), jossa on paikka näytettävälle karttakuvalle. Aloitusnäkymäksi tähän voi ladata
getMap-kyselyllä vapaavalintaiset karttakerrokset ja vapaavalintaisen kohdan kartalta. Tässä
voi haluttaessa käyttää myös XML-datassa olevia rajakoordinaatteja. Käyttöliittymän
koodissa on valmiina esimerkki aloitusnäkymän lataamisesta.
• Käyttöliittymään tulee valintalaatikot (checkbox) kutakin karttakerrosta kohti. Painettaessa
Päivitä-nappia sovellus pyytää palvelimelta uuden karttakuvan, jossa on rastitettujen
valintalaatikoiden mukaiset kerrokset. Kuvan hakeminen palvelimelta on syytä tehdä
omassa säikeessään.
• Lisäksi toteutetaan käyttöliittymään kartalla liikkuminen (oikealle, vasemmalle, ylös ja alas)
sekä kartan lähennys ja loitonnus. Painettaessa liikkumisnuolia tai zoomauspainikkeita
muutetaan siis kartan koordinaatteja vastaavasti ja tehdään pavelimelle uusi getMap-pyyntö.
Liikkumisen ja zoomauksen siirtymät saa päättää vapaasti. Jälleen karttakuvan lataus
tehdään omassa säikeessään.
• Käyttöliittymän runko on tehty valmiiksi helpottamaan sovelluksen rakentamista, mutta sitä
saa myös halutessaan vapaasti muokata (ja parannella esteettisesti ja toiminnallisesti).
Käyttöliittymäluokan koodissa on merkattu TODO-kommenteilla kohtia, joita työhön tulee
toteuttaa.
• Muun muassa se, miten ja mihin getMap-pyynnöissä tarvittavat parametrit sovelluksessa
säilötään ja miten niitä muutetaan ennen uuden karttakuvan hakemista, ovat ratkaistavia
ongelmia. Myös työn luokkarakennetta on hyvä miettiä; luokkia saa tarpeen mukaan lisäillä.
