import React from 'react';
import {
  SafeAreaView,
  Platform,
  StyleSheet,
  ScrollView,
  NativeModules,
  TextInput,
  View,
  ActivityIndicator,
  Text,
  TouchableOpacity,
  Alert,
  StatusBar,
} from 'react-native';

import {
  Header,
  LearnMoreLinks,
  Colors,
  DebugInstructions,
  ReloadInstructions,
} from 'react-native/Libraries/NewAppScreen';

//import CheckoutPageView from './CheckoutPageView.js';

var StripeBridge = NativeModules.StripeBridge;
var CheckoutPageView;


//import StripeBridge from './StripeBridge';

class App extends React.Component {
  state = {
    loadingCardButton: false,
    ccname: 'Maria Bernasconi',
    year: '22',
    ccnumber: '4242424242424242',
    month: '12',
    cvc: '123',
    isIos: Platform.select({
      'ios': true,
      'android': false,
    })
    
  };

  constructor() {
    super();
    this._clickEvent = this._clickEvent.bind(this);
    this.Pay = this.Pay.bind(this);
    if (Platform.OS === 'android') {
      CheckoutPageView = require('./CheckoutPageView.js');
    }
  }

  // event listener to update the values of the different inputs
  _onCCnumberChange = text => {
    this.setState({ccnumber: text});
  };
  _onCCnameChange = text => {
    this.setState({name: text});
  };
  _onCCmonthChange = text => {
    this.setState({month: text});
  };
  _onCCyearChange = text => {
    this.setState({year: text});
  };
  _onCCcvvChange = text => {
    this.setState({cvc: text});
  };

  Pay() {
    const {ccnumber, year, month, cvc} = this.state;
    this.setState({loadingCardButton: true});
    //https://veml8u1rjb.execute-api.us-west-1.amazonaws.com/beta/payment
    //http://localhost:3001/createStripePaymentIntent

    
    fetch('https://veml8u1rjb.execute-api.us-west-1.amazonaws.com/beta/payment', {
      method: 'POST',
      headers: {
        Accept: 'application/json',
        'Content-Type': 'application/json',
      },
    })
      .then(response => response.json())
      .then(responseJson => {
        const clientSecret = responseJson.setupIntentId;
        console.log(responseJson.setupIntentId);
        StripeBridge.createPayment(
          responseJson.setupIntentId,
          ccnumber,
          month,
          year,
          cvc,
          (error, res, payment_method) => {
            if (res == 'SUCCESS') {
              this.setState({loadingCardButton: false});
              Alert.alert('Stripe Payment', 'Your Stripe payment succeeded', [
                {text: 'OK', onPress: () => console.log('OK Pressed')},
              ]);
            }
          },
        );
      })
      .catch(error => {
        console.error(error);
      });

      
  };

  //PART 3: Added event receive, i.e. click from native button.
  _clickEvent(event) {
    console.warn(event.nativeEvent.customNativeEventMessage);
    this.Pay();
  }



  render() {
    return this.state.isIos ? (
      <View style={{paddingTop: 100, backgroundColor: '#1e1f34', flex: 1}}>
      
      <View style={styles.flowRight}>
        <TextInput
          editable={true}
          style={styles.searchInput}
          autoCapitalize={'words'}
          keyboardType={'default'}
          placeholder="Name on card"
          onChangeText={text => this._onCCnameChange(text)}
          autoCorrect={false}
          multiLine={false}
          placeholderTextColor="#7a7d85"
          selectionColor="white"
          autoCompleteType="off"
          textContentType="none"
        />
      </View>
      <View style={styles.flowRight}>
        <TextInput
          editable={true}
          maxLength={16}
          style={styles.searchInput}
          keyboardType={'number-pad'}
          placeholder="Card Number"
          onChangeText={text => this._onCCnumberChange(text)}
          autoCorrect={false}
          multiLine={false}
          autoCapitalize={'none'}
          placeholderTextColor="#7a7d85"
          selectionColor="white"
          autoCompleteType="off"
          textContentType="none"
        />
      </View>
      <View style={styles.flowRight}>
        <TextInput
          maxLength={2}
          editable={true}
          style={styles.searchInput}
          keyboardType={'number-pad'}
          placeholder="MM"
          onChangeText={text => this._onCCmonthChange(text)}
          autoCorrect={false}
          multiLine={false}
          placeholderTextColor="#7a7d85"
          selectionColor="white"
          autoCompleteType="off"
          textContentType="none"
        />
        <TextInput
          maxLength={2}
          editable={true}
          style={styles.searchInput}
          keyboardType={'number-pad'}
          placeholder="YY"
          onChangeText={text => this._onCCyearChange(text)}
          autoCorrect={false}
          multiLine={false}
          placeholderTextColor="#7a7d85"
          selectionColor="white"
          autoCompleteType="off"
          textContentType="none"
        />
      </View>
      <View style={styles.flowRight}>
        <TextInput
          maxLength={3}
          editable={true}
          style={styles.searchInput}
          keyboardType={'number-pad'}
          placeholder="CVC"
          onChangeText={text => this._onCCcvvChange(text)}
          autoCorrect={false}
          multiLine={false}
          placeholderTextColor="#7a7d85"
          selectionColor="white"
          autoCompleteType="off"
          textContentType="none"
        />
      </View>

      {this.state.loadingCardButton ? (
        <View style={{paddingTop: 50}}>
          <ActivityIndicator />
        </View>
      ) : (
        <TouchableOpacity
          style={styles.blueButton}
          onPress={() => this.Pay()}>
          <Text>Pay</Text>
        </TouchableOpacity>
      )}
    </View>

    ) : (
      //<Text>here2</Text>
    
    
      /*<View>
        {this.returnView(temp)}
      </View>*/
      <CheckoutPageView style={{flex:1}} onClick={this._clickEvent}></CheckoutPageView>
      
     /*<View>
      {temp ? (
        <Text>yes</Text>
      ) : (
        //<Text>whats up</Text>
        <CheckoutPageView style={{flex:1}} onClick={this._clickEvent}></CheckoutPageView>
      )}
      </View>*/
  )}
}

const styles = StyleSheet.create({
  blueButton: {
    marginTop: 50,
    height: 40,
    justifyContent: 'center',
    alignSelf: 'center',
    alignItems: 'center',
    width: 300,
    backgroundColor: '#5ed9f5',
    borderWidth: 1,
    borderColor: '#5ed9f5',
    borderRadius: 0,
  },
  searchInput: {
    color: 'white',
    height: 45,
    paddingLeft: 10,
    flex: 1,
    fontSize: 14,
    borderColor: '#48BBEC',
    backgroundColor: '#1e1f34',
  },
  flowRight: {
    flexDirection: 'row',
    alignItems: 'center',
    alignSelf: 'stretch',
    borderBottomWidth: 1,
    borderBottomColor: '#7a7d85',
  },
});

export default App;