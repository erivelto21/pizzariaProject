<h1>PizzariaProject</h1>
<p>PizzariaProject is a Rest Api of an e-commerce project with only credit card as method of payment with study purpose that sells pizza, being made in java with the following technologies:</p>
<p>-spring boot.</p>
<p>-spring data.</p>
<p>-spring rest.</p>
<p>-spring jersey.</p>
<p>-spring security.</p>
<p>-spring web.</p>
<p>-hibernate.</p>
<p>-jjwt.</p>
<p>-maven.</p>
<p>-JUnit.</p>
<p>-PostgreSQL.</p>
<p>this repository together with pizzariaProjectAngular repository from the back end and front end of the project. Any criticism or idea feel free to say</p>

<h3>API Endpoints</h3>
<p>any endpoint needs /pizzaria before. ex: /pizzaria/flavor</p>
<h6>the endpoints that do not need authentication are:<h6>
  <ul>
    <li>/flavor - METHOD GET - Request Body Parameter: none - this endpoint gets all the flavors registered.</li>
    <li>/pizza - METHOD GET - Request Body Parameter: none - this endpoint gets a list of the most ordered flavors.</li>
    <li>/login - METHOD POST - Request Body Parameter: SystemUser object - this endpoint is where you log in.</li>
    <li>/user - METHOD POST - Request Body Parameter: SystemUser object - this endpoint is where do you register.</li>
    <li>/user/phone - METHOD PUT - Request Body Parameter: SystemUser object - this endpoint updates system user phone number.</li>
    <li>/user/address - METHOD PUT - Request Body Parameter: SystemUser object - this endpoint updates system user address.</li>
  </ul>
<h6>the endpoints that need authentication are:<h6>
  <ul>
    <li>/order/{id} - METHOD GET - Request Body Parameter: none - this endpoint searches an order by its ID.</li>
    <li>/order/user/{id} - METHOD GET - Request Body Parameter: none - this endpoint searches an order by its system user id.</li>
    <li>/payment/creditcard - METHOD POST - Request Body Parameter: Customer object - this endpoint makes a payment with a credit card.</li>
  </ul>
  <h6>My API is deployed on heroku if you wanna give a try the url is below: </h6>
  <p>https://app-pizzaria-rest.herokuapp.com<p>
