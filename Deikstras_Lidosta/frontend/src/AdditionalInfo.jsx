import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

const AdditionalInfo = ({ selectedInfo }) => {
    console.log(selectedInfo);
    /*useEffect(() => {
        console.log(selectedInfo);
    }, [selectedInfo]);*/

    return (
        <div className="">
            <div>
                <p>Hello world</p>
            </div>
            <div>
                <table>
                    <thead></thead>
                    <tr>
                        <th></th>
                        <th></th>
                        <th></th>
                    </tr>
                </table>
            </div>
        </div>
    );
};

export default AdditionalInfo;
