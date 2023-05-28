# LIDOJUMU PĀRVALDĪBAS SISTĒMA

## Programmatūras iecere
Šī programmatūra nodrošina lietotājam atlasīt reisus, izmantojot lietotājam padotos kritērijus pēc izlidošanas pilsētas, ielidošanas pilsētu un filtriem kā cena, distance un pieturvietu skaitu.

## Projekta autori

* Aivars Orups - ao20023,
* Gvido Igaunis - gi20003,
* Indriķis Paiders - ip18089,
* Kārlis Bošs - kb20074,
* Māris Kalniņš - mk20126

Lietojumprogrammas projekts tika izgatavots DatZ4019 — Objektorientētā programmēšana kursa projekta ietvarā pēc pašu izstrādātāju iniciatīvas.
>LU DF, 3. kurss, m.g. 2022./2023.

## Projekta iedarbināšanas instrukcija
Lai palaistu *back-end*:
1) Nepieciešams installet Postgre klientu (download: https://www.postgresql.org/download/) un palaist skriptus iekš `pgAdmin` datu bāzes klienta iekš mapītes `Deikstras_Lidosta/db/PostgrSql`
2) Atvērt projektu `Deikstras_Lidosta` IntelliJ Idea vai Eclipse vai jebkurā citā izstrādes rīkā.
3) `Run` projektu.

Lai palaistu *front-end*:
1) Izmantojot termināli, ieejam mapītē `cd Deikstras_Lidosta/frontend/`
2) `npm i`, lai uzinstallētu nepieciešamās React.js izmantotās pakotnes (komponentes & bibliotēkas)
3) `npm run start`, lai uzsāktu lietotnes saskarnes projektu
4) `http://localhost:3000/` (default port: 3000), lai no tīmeklī varētu izmantot lietotni.

### NOTE!
Kad tiek palaists *front-end*, tad pievērst uzmanību, ka ir jāaugšupielādē sākotnējie dati caur `Upload File` pogu. Augšupielādējamo faila nosaukums ir `origin_data_input.tsv` no mapītes `Deikstras_Lidosta`.

## Lietotāju saskarne
![Reisa detalizētais skats](https://media.discordapp.net/attachments/1104045709151457384/1112382847710396447/image.png?width=708&height=676)

## URL klašu diagramma
![URL klašu diagramma](https://cdn.discordapp.com/attachments/1104045709151457384/1112449211821211781/OOP_UML.drawio_1.png)
