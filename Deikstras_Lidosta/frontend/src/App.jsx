import React, { useState, useEffect } from 'react';
import { Dropdown, FormControl, Button, Form } from 'react-bootstrap';
import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';
import './resources/css/App.css';
import "bootstrap/dist/css/bootstrap.min.css";
import Modal from 'react-modal';
import {getAirportList, getAllFilters, getAllFlights} from './Api.jsx';

const App = () => {
  const [selectedDate, setSelectedDate] = useState(new Date());
  const [searchTextOrigin, setSearchTextOrigin] = useState('');
  const [searchTextDestination, setSearchTextDestination] = useState('');
  const [selectedParam, setSelectedParam] = useState('Choose Param');
  const [isSelectedParam, setIsSelectedParam] = useState(false);
  const [selectedItemOrigin, setSelectedItemOrigin] = useState('Select Flight Origin');
  const [selectedItemDestination, setSelectedItemDestination] = useState('Select Flight Destination');
  const [selectedItemOriginShow, setSelectedItemOriginShow] = useState('Select Flight Origin');
  const [selectedItemDestinationShow, setSelectedItemDestinationShow] = useState('Select Flight Destination');
  const [airportList, setAirportList] = useState([]);
  const [allFilters, setAllFilters] = useState([]);
  const [selectedFlight, setSelectedFlight] = useState([]);
  const [dialogOpen, setDialogOpen] = useState(false);
  const [submitted, setSubmitted] = useState(false);
  const [flightData,setFlightData] = useState([]);
  const [airportMap,setAirportMap] = useState([]);
  const [paramValue, setParamValue] = useState('');

  const handleParamValueChange = (event) => { setParamValue(Number(event.target.value)); };


  useEffect(() => {
    getAirportList(setAirportList)
  }, [])

  useEffect(() => {
    getAllFilters(setAllFilters)
  }, [])

  useEffect(() => {
    getAllFlights(selectedItemOrigin,selectedItemDestination,selectedParam,paramValue,setFlightData,airportList,setAirportMap)
  }, [])
  
  const handleSearchOrigin = (event) => {
    setSearchTextOrigin(event.target.value);
  };

  const handleSearchDestination = (event) => {
    setSearchTextDestination(event.target.value);
  };

  const handleDateChange = date => {
    setSelectedDate(date);
  };

  const openDialog = (flightData) => {
    setSelectedFlight(flightData);
    setDialogOpen(true);
  };
  
  const closeDialog = () => {
    setSelectedFlight([]);
    setDialogOpen(false);
  };

  const handleButtonClick = async () => {
    await getAllFlights(selectedItemOrigin,selectedItemDestination,selectedParam,paramValue,setFlightData,airportList,setAirportMap);
    setSubmitted(true);
  };

  const handleSelectedParam = () => {
    setIsSelectedParam(true);
  };

  const filteredOriginList = airportList.filter(item =>
    item.cityName.toLowerCase().includes(searchTextOrigin.toLowerCase())
  );

  const filteredDestinationList = airportList.filter(item =>
    item.cityName.toLowerCase().includes(searchTextDestination.toLowerCase())
  );

  const isOriginEmpty = searchTextOrigin.trim() !== '' && filteredOriginList.length === 0;
  const isDestinationEmpty = searchTextDestination.trim() !== '' && filteredDestinationList.length === 0;
  
  return (
    <div className="container">
      <h1 className="topcenterHead">Djikstra Airport ✈️</h1>
      <div className="dropdownContainer">
        <Dropdown className='selectionItems'>
          <Dropdown.Toggle variant="secondary">
            {selectedItemOriginShow}
          </Dropdown.Toggle>

          <Dropdown.Menu variant="dark">
            <FormControl
              type="text"
              placeholder="Search"
              value={searchTextOrigin}
              onChange={handleSearchOrigin}
            />
            {isOriginEmpty ? (
              <Dropdown.Item disabled>No items found</Dropdown.Item>
            ) : (
              filteredOriginList.map((item, index) => (
                <Dropdown.Item key={index} onClick={() => {setSelectedItemOrigin(item.airportId);setSelectedItemOriginShow(item.cityName)}}>
                  {item.cityName}
                </Dropdown.Item>
              ))
            )}
          </Dropdown.Menu>
        </Dropdown>
        <Dropdown className="selectionItems">
          <Dropdown.Toggle variant="secondary">
          {selectedItemDestinationShow}
          </Dropdown.Toggle>

          <Dropdown.Menu variant="dark">
            <FormControl
              type="text"
              placeholder="Search"
              value={searchTextDestination}
              onChange={handleSearchDestination}
            />
            {isDestinationEmpty ? (
              <Dropdown.Item disabled>No items found</Dropdown.Item>
            ) : (
              filteredDestinationList.map((item, index) => (
                <Dropdown.Item key={index} onClick={() => {setSelectedItemDestination(item.airportId);setSelectedItemDestinationShow(item.cityName)}}>
                  {item.cityName}
                </Dropdown.Item>
              ))
            )}
          </Dropdown.Menu>
        </Dropdown>

        <Dropdown className="selectionItems">
        <Dropdown.Toggle id="dropdown-button-dark-example1" variant="secondary">
          {selectedParam.charAt(0).toUpperCase() + selectedParam.slice(1)}
        </Dropdown.Toggle>

        <Dropdown.Menu variant="dark">
          {allFilters.map((item, index) => (
            <Dropdown.Item key={index} onClick={() => { setSelectedParam(item); handleSelectedParam() }}>
              {item.charAt(0).toUpperCase() + item.slice(1)}
            </Dropdown.Item>
          ))}
        </Dropdown.Menu>
      </Dropdown>


      { isSelectedParam && (
        <Form className="valueTBox">
        <Form.Group controlId="exampleForm.ControlInput1">
          <FormControl type="number" placeholder="Enter Value" value={paramValue} onChange={handleParamValueChange}/>
        </Form.Group>
      </Form>
      )}

      </div>
      <Button variant="primary" className="selectionItems" onClick={handleButtonClick}>
      Submit
    </Button>
    {submitted && flightData.length > 0 && (
      <table className="tabulasstilinsh">
        <thead>
          <tr>
            <th>Origin</th>
            <th>Destination</th>
            <th>Stopovers</th>
            <th>Total price</th>
            <th>Total distance</th>
          </tr>
        </thead>
        <tbody>
          {flightData.map((array, index) => {
            const stopoverCount = array.flight.length - 1;
            const hasStopovers = stopoverCount > 0;
            return (
              <tr key={index}>
                <td>{airportMap.get(array.flight[0].fromId)}</td>
                <td>{airportMap.get(array.flight[array.flight.length-1].toId)}</td>
                <td onClick={hasStopovers ? () => openDialog(array.flight) : null}>
                  {hasStopovers ? `${stopoverCount} stopover${stopoverCount === 1 ? '' : 's'}` : 'Non-stop'}
                </td>
                <td>{array.price}</td>
                <td>{array.distance}</td>
              </tr>
            );
          })}
        </tbody>
      </table>
    )}

    <Modal
      isOpen={dialogOpen}
      onRequestClose={closeDialog}
      className="modal-content"
      overlayClassName="modal-overlay"
      center
      ariaHideApp={false}
    >
      <h2 className="headStopover">Stopovers</h2>
      <table className="tabulamodal">
        <thead>
          <tr>
            <th>Flight ID</th>
            <th>From ID</th>
            <th>To ID</th>
            <th>Price</th>
            <th>Distance</th>
          </tr>
        </thead>
        <tbody>
          {selectedFlight.map((flight, index) => (
            <tr key={index}>
              <td>{flight.flightId}</td>
              <td>{airportMap.get(flight.fromId)}</td>
              <td>
                {index === selectedFlight.length - 1 ? (
                    airportMap.get(flight.toId)
                ) : (
                  <>
                    {airportMap.get(flight.toId)} - <span className='stops'> stopover</span>
                  </>
                )}
              </td>
              <td>{flight.price}</td>
              <td>{flight.distance}</td>
            </tr>
          ))}
        </tbody>
      </table>
      <button className="btn btn-primary modalBtn border-2 border-dark" onClick={closeDialog}>Close</button>
    </Modal>
    </div>
  );
};

export default App;