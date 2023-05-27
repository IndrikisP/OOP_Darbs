import React, { useState, useEffect } from 'react';
import { Dropdown, FormControl, Button } from 'react-bootstrap';
import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';
import './resources/css/App.css';
import "bootstrap/dist/css/bootstrap.min.css";
import Modal from 'react-modal';
import { getAirportList, getAllFilters, getFlightInfo } from './Api.jsx';

const App = () => {
  const [selectedDate, setSelectedDate] = useState(new Date());
  const [searchTextOrigin, setSearchTextOrigin] = useState('');
  const [searchTextDestination, setSearchTextDestination] = useState('');
  const [selectedItemSort, setSelectedItemSort] = useState('Sort by');
  const [selectedItemOrigin, setSelectedItemOrigin] = useState('Select Flight Origin');
  const [selectedItemDestination, setSelectedItemDestination] = useState('Select Flight Destination');
  const [tableData, setTableData] = useState([]);
  const [airportList, setAirportList] = useState([]);
  const [allFilters, setAllFilters] = useState([]);
  const [flightInfo, setFlightInfo] = useState([]);
  const [selectedFlight, setSelectedFlight] = useState([]);
  const [dialogOpen, setDialogOpen] = useState(false);


  const currentDate = new Date();

  useEffect(() => {
    getAirportList(setAirportList)
  }, [])

  useEffect(() => {
    getAllFilters(setAllFilters)
  }, [])

  useEffect(() => {
    getFlightInfo(setFlightInfo)
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

  const filteredOriginList = airportList.filter(item =>
    item.airport.toLowerCase().includes(searchTextOrigin.toLowerCase())
  );

  const filteredDestinationList = airportList.filter(item =>
    item.airport.toLowerCase().includes(searchTextDestination.toLowerCase())
  );

  const isOriginEmpty = searchTextOrigin.trim() !== '' && filteredOriginList.length === 0;
  const isDestinationEmpty = searchTextDestination.trim() !== '' && filteredDestinationList.length === 0;

  const handleButtonClick = () => {
    const formData = {
      origin: selectedItemOrigin,
      destination: selectedItemDestination,
      date: selectedDate ? selectedDate.toDateString() : '',
      sort: selectedItemSort,
    };
    setTableData(() => [formData]);
  };
  
  return (
    <div className="container">
      <h1 className="topcenterHead">Deikstras Lidosta ✈️</h1>
      <div className="dropdownContainer">
        <Dropdown className='selectionItems'>
          <Dropdown.Toggle variant="secondary">
            {selectedItemOrigin}
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
                <Dropdown.Item key={index} onClick={() => setSelectedItemOrigin(item.airport)}>
                  {item.airport}
                </Dropdown.Item>
              ))
            )}
          </Dropdown.Menu>
        </Dropdown>
        <Dropdown className="selectionItems">
          <Dropdown.Toggle variant="secondary">
          {selectedItemDestination}
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
                <Dropdown.Item key={index} onClick={() => setSelectedItemDestination(item.airport)}>
                  {item.airport}
                </Dropdown.Item>
              ))
            )}
          </Dropdown.Menu>
        </Dropdown>

        <Dropdown className="selectionItems">
        <Dropdown.Toggle id="dropdown-button-dark-example1" variant="secondary">
          {selectedItemSort}
        </Dropdown.Toggle>

        <Dropdown.Menu variant="dark">
          {allFilters.map((item, index) => (
            <Dropdown.Item key={index} onClick={() => setSelectedItemSort(item)}>
              {item}
            </Dropdown.Item>
          ))}
        </Dropdown.Menu>
      </Dropdown>

      <Dropdown className='selectionItems'>
      <Dropdown.Toggle variant="secondary" id="dropdown-button">
        {selectedDate ? selectedDate.toDateString() : 'Select Date'}
      </Dropdown.Toggle>
      <Dropdown.Menu>
          <DatePicker
            selected={selectedDate}
            onChange={handleDateChange}
            dateFormat="dd/MM/yyyy"
            placeholderText="Select a date"
            className="form-control"
            minDate={currentDate}
          />
      </Dropdown.Menu>
    </Dropdown>
      </div>
      <Button variant="primary" className="selectionItems" onClick={handleButtonClick}>
      Submit
    </Button>
    {flightInfo.length > 0 && (
      <table className="tabulasstilinsh">
        <thead>
          <tr>
            <th>Origin</th>
            <th>Destination</th>
            <th>Stopovers</th>
          </tr>
        </thead>
        <tbody>
          {flightInfo.map((array, index) => {
            const stopoverCount = array.length - 1;
            const hasStopovers = stopoverCount > 0;

            return (
              <tr key={index}>
                <td>{array[0].FromId}</td>
                <td>{array[array.length - 1].ToId}</td>
                <td onClick={hasStopovers ? () => openDialog(array) : null}>
                  {hasStopovers ? `${stopoverCount} stopover${stopoverCount === 1 ? '' : 's'}` : 'Non-stop'}
                </td>
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
    >
      <h2 className="headStopover">Stopovers</h2>
      <table className="tabulamodal">
        <thead>
          <tr>
            <th>Flight ID</th>
            <th>From ID</th>
            <th>To ID</th>
          </tr>
        </thead>
        <tbody>
          {selectedFlight.map((flight, index) => (
            <tr key={index}>
              <td>{flight.FlightId}</td>
              <td>{flight.FromId}</td>
              <td>
                {index == selectedFlight.length - 1 ? (
                  flight.ToId
                ) : (
                  <>
                    {flight.ToId} - <span className='stops'> stopover</span>
                  </>
                )}
              </td>
            </tr>
          ))}
        </tbody>
      </table>
      <button className="modalBtn" onClick={closeDialog}>Close</button>
    </Modal>
    </div>
  );
};

export default App;