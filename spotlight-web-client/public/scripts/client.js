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
        <h2>{this.props.name}</h2>
        <table>
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
        <h1>Order Book</h1>
          <div style={{display: 'inline-block'}}>
            <BookSide name="Bids" />
          </div>
          <div style={{display: 'inline-block'}}>
            <BookSide name="Offers" orders={this.props.offers} /> 
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
         <h1>Order cancel request form</h1>
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
        <CancelOrderForm orders={this.state.data} onCancelRequestSubmitted={this.onCancelRequestSubmitted} />
        <AddOfferForm onOfferSubmitted={this.onOfferSubmitted} />
        <OrderBook offers={this.state.data}/>
      </div>
    );
  }
});

var AddOfferForm = React.createClass({
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
    this.props.onOfferSubmitted({id: Date.now(), price: price});
  },
  render: function() {
    return (
      <form onSubmit={this.handleSubmit}>
        <p>Price: $<input type="text" onChange={this.onPriceChange} value={this.state.price}/></p>
        <input type="submit" disabled={!this.state.price} value="Submit offer" />
      </form>
    );
  }
});

ReactDOM.render(
  <OrderBookSimulator />,
  document.getElementById('content')
);
