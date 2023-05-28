import Axios from "axios";

export const getAirportList = async (setAirportList) => {
    try {
        const resp = await Axios.get("http://localhost:8080/get/allAirports");
        const data = resp.data;
        //console.log(data);
        const sortedData = data.sort((a, b) => {
            const airportA = a.cityName.toLowerCase();
            const airportB = b.cityName.toLowerCase();
            return airportA.localeCompare(airportB);
        });
        setAirportList(sortedData);
    } catch (error) {
        console.error("Error fetching airport data:", error);
    }
};

export const getAllFilters = (setAllFilters) => {
    const data = [
        'price',
        'distance',
        'stop'
    ];
    setAllFilters(data);
};

export const getAllFlights = async (selectedItemOrigin,selectedItemDestination,selectedParam,paramValue,setFlightData,airportList,setAirportMap) => {
    try {
        if(paramValue === '') paramValue = 0;
        const resp = await Axios.get('http://localhost:8080/get/paths?fromid='+selectedItemOrigin+'&toid='+selectedItemDestination+'&paramname='+selectedParam+'&parameter='+paramValue);
        const data = resp.data;
        console.log(data);
        const airportMap = new Map();
        //console.log(airportList);
        for (const airport of airportList) {
            airportMap.set(airport.airportId, airport.cityName);
        }
        setAirportMap(airportMap);
        setFlightData(data);

        //console.log(airportMap);
    } catch (error) {
        console.error("Error fetching flights data:", error);
    }
}

export const uploadDoc = async (formData) =>{
    try{
        const resp = await Axios.post('http://localhost:8080/add/document',formData);
        console.log(resp);
    }
    catch (error) {
        console.error("Error uploading data:", error);
    }
}