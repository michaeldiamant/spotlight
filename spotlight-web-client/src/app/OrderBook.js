'use strict';

import React from 'react';
import BookSide from './BookSide';

class OrderBook extends React.Component {
  render() {
    return (
      <div>
        <div className="row">
          <div className="col-md-4">
            <p className="text-center"><strong>Order Book</strong></p>
          </div>
        </div>  
        <div className="row">
          <div className="col-md-2">
            <BookSide name="Bids" orders={this.props.bids} />
          </div>
          <div className="col-md-2">
            <BookSide name="Offers" orders={this.props.offers} /> 
          </div>
        </div>
      </div>
    );
  }
};

export default OrderBook;
