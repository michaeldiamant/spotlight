'use strict';

import React from 'react';
import PriceLevel from './PriceLevel';

class BookSide extends React.Component {
  render() {
    var safeOrders = (this.props.orders ? this.props.orders : [])
    var renderedOrders = safeOrders.map(function(order) {
      return (
        <PriceLevel key={order.id} price={order.price} size="1" /> 
      );
    });
    return (
      <div>
        <strong>{this.props.name}</strong>
        <table className="table table-striped table-condensed table-bordered">
          <tbody>
            {renderedOrders}
          </tbody>
        </table>
      </div>
    );
  }
};

export default BookSide;
