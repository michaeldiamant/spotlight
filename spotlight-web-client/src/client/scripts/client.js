'use strict';

import React from 'react';
import ReactDOM from 'react-dom';
import Select from 'react-select';
import 'react-select/dist/react-select.css';
import 'bootstrap/dist/css/bootstrap.css';

var PriceLevel = React.createClass({
  render: function() {
    return (
      <tr> 
        <td>{this.props.price}</td>
        <td>{this.props.size}</td>
      </tr>
    );
  }
});

var BookSide = React.createClass({
  render: function() {
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
});

var OrderBook = React.createClass({
  render: function() {
    return (
      <div>
        <div className="row">
          <div className="col-md-4">
            <p className="text-center"><strong>Order Book</strong></p>
          </div>
        </div>  
        <div className="row">
          <div className="col-md-2">
            <BookSide name="Bids" />
          </div>
          <div className="col-md-2">
            <BookSide name="Offers" orders={this.props.offers} /> 
          </div>
        </div>
      </div>
    );
  }
});

var OrderBookSearch = React.createClass({
  onSelectChange: function (selectItem) {
    this.props.onOrderSelected(selectItem.value);
  },
  render: function() {
    var safeOrders = (this.props.orders ? this.props.orders : [])
    var renderedOrders = safeOrders.map(function(order) {
      return (
        { value: order, label: order.id }
      );
    });
    return (
       <Select name="order-search" value="" options={renderedOrders} onChange={this.onSelectChange}/>
    );
  }
});

var CancelOrderForm = React.createClass({
  getInitialState: function() {
    return {
      selectedOrder: null
    };
  },
  onOrderSelected: function(order) {
    this.setState({ selectedOrder: order });
  },
  handleSubmit: function(e) {
    e.preventDefault();
    if (!this.state.selectedOrder) {
      return;
    }
    var selectedId = this.state.selectedOrder.id;
    this.setState({ selectedOrder: null });
    this.props.onCancelRequestSubmitted({ id: Date.now(), orderId: selectedId });
  },
  render: function() {
    return (
      <div>
         <strong>Order cancel request form</strong>
         <OrderBookSearch orders={this.props.orders} onOrderSelected={this.onOrderSelected} />
         {
           this.state.selectedOrder && <div>
             <p>Selected order ID: {this.state.selectedOrder.id}</p>
             <p>Selected order price: {this.state.selectedOrder.price}</p>
           </div>
         }
        <form onSubmit={this.handleSubmit}>
          <input type="hidden" value="{this.state.selectedOrder.id}" />
          <input type="submit" disabled={!this.state.selectedOrder} value="Cancel order" />
        </form>
      </div>
    );
  }
});

var OrderBookSimulator = React.createClass({
  getInitialState: function() {
    return { data: [] };
  },
  onOfferSubmitted: function(offer) {
    this.state.data.push(offer)
    this.setState({ data: this.state.data });
  },
  onCancelRequestSubmitted: function(cancelRequest) {
    var filteredOrders = this.state.data.filter(function(o) {
      return o.id != cancelRequest.orderId;
    });
    this.setState({ data: filteredOrders });
  },
  render: function() {
    return (
      <div>
        <div className="row">
          <div className="col-md-2">
            <AddBidForm onBidSubmitted={this.onOfferSubmitted} />
          </div>
          <div className="col-md-2">
            <AddOfferForm onOfferSubmitted={this.onOfferSubmitted} />
          </div>
          <div className="col-md-4">
            <CancelOrderForm orders={this.state.data} onCancelRequestSubmitted={this.onCancelRequestSubmitted} />
          </div>
        </div>
        <div className="row">
          <div className="col-md-8 col-md-offset-4">
            <OrderBook offers={this.state.data}/>
          </div>
        </div>
      </div>
    );
  }
});

var AddOrderForm = React.createClass({
  getInitialState: function(e) {
    return { price: '' };
  },
  onPriceChange: function(e) {
    this.setState({ price: e.target.value });
  },
  handleSubmit: function(e) {
    e.preventDefault();
    var price = this.state.price.trim();
    if (!price) {
      return;
    }
    this.props.onOrderSubmitted({id: Date.now(), price: price, side: this.props.side.toLowerCase() });
  },
  render: function() {
    var label = "Submit " + this.props.side.toLowerCase();
    return (
      <div>
        <strong>Add {this.props.side} form</strong>
        <form onSubmit={this.handleSubmit}>
          <p>Price: $<input type="text" onChange={this.onPriceChange} value={this.state.price}/></p>
          <input type="submit" disabled={!this.state.price} value={label} />
        </form>
      </div>
    );
  }
});

var AddOfferForm = React.createClass({
  render: function() {
    return (
      <AddOrderForm side="Offer" onOrderSubmitted={this.props.onOfferSubmitted} />
    );
  }
});

var AddBidForm = React.createClass({
  render: function() {
    return (
      <AddOrderForm side="Bid" onOrderSubmitted={this.props.onBidSubmitted} />
    );
  }
});

ReactDOM.render(
  <OrderBookSimulator />,
  document.getElementById('app')
);
