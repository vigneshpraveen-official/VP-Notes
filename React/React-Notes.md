<p align="center">
  <img src="/vp-notes.png" alt="Vigneshpraveen Banner" width="1000px" height="280px">
</p>


# 

---

# Comprehensive Guide to React: Core Concepts and Practical Implementations - By Vigneshpraveen

React has evolved significantly since its introduction, with functional components and hooks gradually replacing class components as the preferred approach for building React applications. This comprehensive guide explores all essential React concepts, from components and lifecycle methods to routing and advanced implementation patterns. The following sections provide detailed explanations with practical code examples to illustrate each concept.

# 1. React Components: Class vs Functional

React components are the building blocks of any React application, with two primary approaches to creating them: class components and functional components. Each approach has distinct characteristics, though functional components have become the standard in modern React development.

### Class Components

Class components are ES6 classes that extend from React.Component and must include a render() method that returns JSX (React elements). Class components were the original way to create components in React and provide access to all React features through built-in lifecycle methods and state management. They maintain their own internal state and have full access to the component lifecycle.

```jsx
import React, { Component } from 'react';

class Welcome extends Component {
  constructor(props) {
    super(props);
    this.state = {
      message: 'Hello World'
    };
  }
  
  render() {
    return (
      <div>
        <h1>{this.state.message}</h1>
        <p>Welcome, {this.props.name}</p>
      </div>
    );
  }
}

export default Welcome;
```

Class components require more boilerplate code, as they need a constructor for initializing state and binding methods. They also use the `this` keyword to access props, state, and class methods, which can be a source of confusion for developers new to JavaScript's context behavior. Despite these considerations, class components remain valuable for understanding React's fundamental principles and working with legacy codebases.

### Functional Components

Functional components are JavaScript functions that accept props as an argument and return JSX. Initially, functional components were stateless, but with the introduction of Hooks in React 16.8, they gained the ability to use state and other React features without writing a class.

```jsx
import React, { useState } from 'react';

function Welcome(props) {
  const [message, setMessage] = useState('Hello World');
  
  return (
    <div>
      <h1>{message}</h1>
      <p>Welcome, {props.name}</p>
    </div>
  );
}

export default Welcome;
```

Functional components offer several advantages over class components. They are more concise, easier to understand and test, and less prone to the common pitfalls associated with JavaScript's `this` keyword. Their simplicity also contributes to smaller bundle sizes and potentially better performance. With hooks, functional components can now manage their own state and handle side effects, making them capable of everything class components can do while maintaining a more straightforward syntax.

# 2. React Component Lifecycle

Understanding the component lifecycle is crucial for optimizing React applications and managing side effects appropriately. The lifecycle differs between class components with their explicit methods and functional components using the useEffect hook.

### Class Component Lifecycle Methods

React class components go through a series of lifecycle phases: mounting, updating, and unmounting. Each phase provides methods that execute at specific points in the component's existence.

#### Mounting Phase

The mounting phase occurs when a component is being created and inserted into the DOM. It involves the following methods:

```jsx
import React, { Component } from 'react';

class LifecycleDemo extends Component {
  constructor(props) {
    super(props);
    this.state = { data: 'Initial data' };
    console.log('Constructor executed');
  }
  
  static getDerivedStateFromProps(props, state) {
    console.log('getDerivedStateFromProps executed');
    return null; // Return null to indicate no state update needed
  }
  
  componentDidMount() {
    console.log('Component mounted');
    // Perfect place for API calls
    setTimeout(() => {
      this.setState({ data: 'Updated data after mount' });
    }, 2000);
  }
  
  render() {
    console.log('Render method executed');
    return (
      <div>
        <h2>Lifecycle Demo</h2>
        <p>Data: {this.state.data}</p>
      </div>
    );
  }
}

export default LifecycleDemo;
```

In this example, the component first executes the constructor, initializing the component's state. Next, `getDerivedStateFromProps` is called, allowing the component to update its state based on props changes. The `render` method then creates the component's UI. Finally, after the component is mounted to the DOM, `componentDidMount` executes, which is ideal for data fetching and subscriptions.

#### Updating Phase

The updating phase occurs when a component is re-rendered due to changes in props or state:

```jsx
import React, { Component } from 'react';

class UpdateLifecycleDemo extends Component {
  constructor(props) {
    super(props);
    this.state = { counter: 0 };
  }
  
  static getDerivedStateFromProps(props, state) {
    console.log('Update: getDerivedStateFromProps executed');
    return null;
  }
  
  shouldComponentUpdate(nextProps, nextState) {
    console.log('shouldComponentUpdate executed');
    // Only re-render if counter changes to an even number
    return nextState.counter % 2 === 0 || state.counter % 2 === 0;
  }
  
  getSnapshotBeforeUpdate(prevProps, prevState) {
    console.log('getSnapshotBeforeUpdate executed');
    return { scrollPosition: 100 };
  }
  
  componentDidUpdate(prevProps, prevState, snapshot) {
    console.log('Component updated');
    console.log('Snapshot value:', snapshot);
    if (prevState.counter !== this.state.counter) {
      console.log('Counter value changed from', prevProps.counter, 'to', this.state.counter);
    }
  }
  
  incrementCounter = () => {
    this.setState({ counter: this.state.counter + 1 });
  }
  
  render() {
    console.log('Render method executed');
    return (
      <div>
        <h2>Update Lifecycle Demo</h2>
        <p>Counter: {this.state.counter}</p>
        <button onClick={this.incrementCounter}>Increment</button>
      </div>
    );
  }
}

export default UpdateLifecycleDemo;
```

During updates, `shouldComponentUpdate` determines whether the component should re-render, potentially optimizing performance by preventing unnecessary renders. `getSnapshotBeforeUpdate` allows capture of information from the DOM before it potentially changes, and `componentDidUpdate` runs after the update is committed to the DOM, making it suitable for network requests based on prop changes.

#### Unmounting Phase

The unmounting phase occurs when a component is being removed from the DOM:

```jsx
componentWillUnmount() {
  console.log('Component will unmount');
  // Clean up resources (e.g., cancel network requests, remove event listeners)
  window.removeEventListener('resize', this.handleResize);
  clearInterval(this.timerID);
}
```

The `componentWillUnmount` method is crucial for performing cleanup to prevent memory leaks, such as removing event listeners, canceling network requests, or clearing timers.

### Error Handling Lifecycle Methods

React class components also provide methods for handling errors that occur during rendering:

```jsx
static getDerivedStateFromError(error) {
  // Update state to display a fallback UI
  return { hasError: true };
}

componentDidCatch(error, info) {
  // Log the error to an error reporting service
  console.error('Error caught by component:', error, info);
}
```

These methods allow components to gracefully handle errors and display fallback UIs instead of crashing the entire application.

# 3. React Hooks

Hooks are functions that let you "hook into" React state and lifecycle features from function components. They were introduced in React 16.8 to allow using React features without classes.

### useState Hook

The useState hook enables state management in functional components. It returns a stateful value and a function to update it.

```jsx
import React, { useState } from 'react';

function Counter() {
  const [count, setCount] = useState(0);
  
  return (
    <div>
      <p>You clicked {count} times</p>
      <button onClick={() => setCount(count + 1)}>
        Click me
      </button>
    </div>
  );
}

export default Counter;
```

In this example, `useState(0)` initializes a state variable `count` with the value 0 and provides a `setCount` function to update this value. When the button is clicked, `setCount` is called with the new value, triggering a re-render of the component.

Multiple state hooks can be used to manage different state values:

```jsx
function UserForm() {
  const [name, setName] = useState('');
  const [age, setAge] = useState(0);
  const [email, setEmail] = useState('');
  
  return (
    <form>
      <input 
        type="text" 
        value={name} 
        onChange={(e) => setName(e.target.value)} 
        placeholder="Name" 
      />
      <input 
        type="number" 
        value={age} 
        onChange={(e) => setAge(e.target.value)} 
        placeholder="Age" 
      />
      <input 
        type="email" 
        value={email} 
        onChange={(e) => setEmail(e.target.value)} 
        placeholder="Email" 
      />
    </form>
  );
}
```


### useEffect Hook

The useEffect hook lets you perform side effects in functional components. It serves the same purpose as `componentDidMount`, `componentDidUpdate`, and `componentWillUnmount` in class components.

```jsx
import React, { useState, useEffect } from 'react';

function DataFetcher() {
  const [data, setData] = useState(null);
  const [loading, setLoading] = useState(true);
  
  useEffect(() => {
    const fetchData = async () => {
      try {
        // Simulate API call
        const response = await fetch('https://api.example.com/data');
        const result = await response.json();
        setData(result);
        setLoading(false);
      } catch (error) {
        console.error('Error fetching data:', error);
        setLoading(false);
      }
    };
    
    fetchData();
    
    // Cleanup function (equivalent to componentWillUnmount)
    return () => {
      console.log('Component unmounting, cleaning up...');
      // Cancel any pending requests or subscriptions
    };
  }, []); // Empty dependency array means this effect runs once after initial render
  
  return (
    <div>
      {loading ? (
        <p>Loading data...</p>
      ) : (
        <div>
          <h2>Data Loaded</h2>
          <pre>{JSON.stringify(data, null, 2)}</pre>
        </div>
      )}
    </div>
  );
}

export default DataFetcher;
```

The dependency array (second argument to useEffect) controls when the effect runs:

- If omitted, the effect runs after every render
- If empty `[]`, the effect runs only after the initial render (like `componentDidMount`)
- If containing values, the effect runs when any of those values change


### useHistory Hook (now useNavigate)

The useHistory hook (now replaced by useNavigate in React Router v6) provides access to the history instance for navigation:

```jsx
import React from 'react';
import { useHistory } from 'react-router-dom'; // For React Router v5

function NavigationExample() {
  const history = useHistory();
  
  const navigateToHome = () => {
    history.push('/home');
  };
  
  const navigateBack = () => {
    history.goBack();
  };
  
  return (
    <div>
      <h2>Navigation Example</h2>
      <button onClick={navigateToHome}>Go to Home</button>
      <button onClick={navigateBack}>Go Back</button>
    </div>
  );
}

export default NavigationExample;
```

In React Router v6, useHistory is replaced with useNavigate:

```jsx
import { useNavigate } from 'react-router-dom'; // For React Router v6

function NavigationExample() {
  const navigate = useNavigate();
  
  return (
    <div>
      <button onClick={() => navigate('/home')}>Go to Home</button>
      <button onClick={() => navigate(-1)}>Go Back</button>
    </div>
  );
}
```


### useRef Hook

The useRef hook creates a mutable object with a `.current` property. It persists across renders and doesn't cause re-renders when its value changes.

```jsx
import React, { useRef, useEffect, useState } from 'react';

function TextInputWithFocusButton() {
  const inputRef = useRef(null);
  const [text, setText] = useState('');
  const prevTextRef = useRef('');
  
  useEffect(() => {
    // Store current text in ref after each render
    prevTextRef.current = text;
  }, [text]);
  
  const focusInput = () => {
    inputRef.current.focus();
  };
  
  const handleChange = (e) => {
    setText(e.target.value);
  };
  
  return (
    <div>
      <input
        ref={inputRef}
        type="text"
        value={text}
        onChange={handleChange}
      />
      <button onClick={focusInput}>Focus Input</button>
      <p>Current text: {text}</p>
      <p>Previous text: {prevTextRef.current}</p>
    </div>
  );
}

export default TextInputWithFocusButton;
```

The useRef hook has several common use cases:

1. Accessing DOM elements directly (as in the example above)
2. Storing previous values (without triggering re-renders)
3. Persisting values between renders without causing re-renders
4. Storing mutable values that shouldn't trigger updates

# 4. Props and PropTypes in React

Props (short for properties) are the mechanism for passing data from parent to child components in React, making components reusable and composable.

### Basic Props Usage

```jsx
import React from 'react';

// Child component
function Greeting(props) {
  return <h1>Hello, {props.name}!</h1>;
}

// Parent component
function App() {
  return (
    <div>
      <Greeting name="Alice" />
      <Greeting name="Bob" />
    </div>
  );
}

export default App;
```

Props are read-only and should never be modified within the receiving component. This immutability helps maintain a unidirectional data flow, which makes applications easier to understand.

### Destructuring Props

For cleaner code, props can be destructured in the function parameters:

```jsx
function UserProfile({ name, age, email, isAdmin = false }) {
  return (
    <div>
      <h2>{name}</h2>
      <p>Age: {age}</p>
      <p>Email: {email}</p>
      {isAdmin && <p>Admin User</p>}
    </div>
  );
}

// Usage
<UserProfile name="John Doe" age={28} email="john@example.com" />
```


### Children Props

The special `children` prop allows components to render nested content:

```jsx
function Card({ title, children }) {
  return (
    <div className="card">
      <div className="card-header">
        <h3>{title}</h3>
      </div>
      <div className="card-body">
        {children}
      </div>
    </div>
  );
}

// Usage
function App() {
  return (
    <Card title="Welcome">
      <p>This is the card content.</p>
      <button>Click me</button>
    </Card>
  );
}
```


### PropTypes for Type Checking

PropTypes provides runtime type checking for React props, helping catch bugs and document component interfaces:

```jsx
import React from 'react';
import PropTypes from 'prop-types';

function UserProfile({ name, age, email, isAdmin }) {
  return (
    <div>
      <h2>{name}</h2>
      <p>Age: {age}</p>
      <p>Email: {email}</p>
      {isAdmin && <p>Admin User</p>}
    </div>
  );
}

UserProfile.propTypes = {
  name: PropTypes.string.isRequired,
  age: PropTypes.number,
  email: PropTypes.string.isRequired,
  isAdmin: PropTypes.bool
};

UserProfile.defaultProps = {
  age: 0,
  isAdmin: false
};

export default UserProfile;
```

PropTypes include validators for all JavaScript primitives and structures:

```jsx
MyComponent.propTypes = {
  // Basic types
  stringProp: PropTypes.string,
  numberProp: PropTypes.number,
  booleanProp: PropTypes.bool,
  functionProp: PropTypes.func,
  
  // React elements
  elementProp: PropTypes.element,
  
  // Arrays and objects
  arrayProp: PropTypes.array,
  objectProp: PropTypes.object,
  
  // Specific arrays
  numbersArrayProp: PropTypes.arrayOf(PropTypes.number),
  
  // Specific object shape
  userProp: PropTypes.shape({
    id: PropTypes.number.isRequired,
    name: PropTypes.string.isRequired,
    email: PropTypes.string
  }),
  
  // One of specific values
  statusProp: PropTypes.oneOf(['pending', 'active', 'completed']),
  
  // One of specific types
  idProp: PropTypes.oneOfType([
    PropTypes.string,
    PropTypes.number
  ]),
  
  // Instance of a class
  dateProp: PropTypes.instanceOf(Date)
};
```


# 5. Controlled and Uncontrolled Components

React offers two approaches to handling form inputs: controlled components and uncontrolled components. Each has its use cases and trade-offs.

### Controlled Components

In controlled components, form data is handled by React state, making React the "single source of truth" for the input's value:

```jsx
import React, { useState } from 'react';

function ControlledForm() {
  const [formData, setFormData] = useState({
    username: '',
    email: '',
    password: ''
  });
  
  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prevData => ({
      ...prevData,
      [name]: value
    }));
  };
  
  const handleSubmit = (e) => {
    e.preventDefault();
    console.log('Form submitted with data:', formData);
    // Process form submission (API call, etc.)
  };
  
  return (
    <form onSubmit={handleSubmit}>
      <div>
        <label htmlFor="username">Username:</label>
        <input
          type="text"
          id="username"
          name="username"
          value={formData.username}
          onChange={handleChange}
        />
      </div>
      <div>
        <label htmlFor="email">Email:</label>
        <input
          type="email"
          id="email"
          name="email"
          value={formData.email}
          onChange={handleChange}
        />
      </div>
      <div>
        <label htmlFor="password">Password:</label>
        <input
          type="password"
          id="password"
          name="password"
          value={formData.password}
          onChange={handleChange}
        />
      </div>
      <button type="submit">Submit</button>
    </form>
  );
}

export default ControlledForm;
```

Controlled components offer several advantages:

1. Instant validation and feedback
2. Disabling the submit button based on form state
3. Enforcing input formats
4. Conditionally changing other elements based on input
5. Single source of truth for form data

### Uncontrolled Components

Uncontrolled components maintain their own internal state, with React retrieving values using refs when needed:

```jsx
import React, { useRef } from 'react';

function UncontrolledForm() {
  const usernameRef = useRef(null);
  const emailRef = useRef(null);
  const passwordRef = useRef(null);
  
  const handleSubmit = (e) => {
    e.preventDefault();
    const formData = {
      username: usernameRef.current.value,
      email: emailRef.current.value,
      password: passwordRef.current.value
    };
    console.log('Form submitted with data:', formData);
    // Process form submission (API call, etc.)
  };
  
  return (
    <form onSubmit={handleSubmit}>
      <div>
        <label htmlFor="username">Username:</label>
        <input
          type="text"
          id="username"
          name="username"
          ref={usernameRef}
          defaultValue=""
        />
      </div>
      <div>
        <label htmlFor="email">Email:</label>
        <input
          type="email"
          id="email"
          name="email"
          ref={emailRef}
          defaultValue=""
        />
      </div>
      <div>
        <label htmlFor="password">Password:</label>
        <input
          type="password"
          id="password"
          name="password"
          ref={passwordRef}
          defaultValue=""
        />
      </div>
      <button type="submit">Submit</button>
    </form>
  );
}

export default UncontrolledForm;
```

Uncontrolled components have their own benefits:

1. Less code for simple forms
2. Integration with non-React code is easier
3. No re-renders on every keystroke (potentially better performance for very large forms)
4. Useful for integrating with third-party DOM libraries

# 6. Auxiliary Components

Auxiliary components (sometimes called higher-order components or utility components) are reusable components that serve specific structural or functional purposes in a React application.

### Higher-Order Components (HOCs)

Higher-order components are functions that take a component and return a new enhanced component:

```jsx
import React from 'react';

// HOC that adds loading functionality
function withLoading(WrappedComponent) {
  return function WithLoadingComponent({ isLoading, ...props }) {
    if (isLoading) {
      return <div>Loading...</div>;
    }
    return <WrappedComponent {...props} />;
  };
}

// Component that will be wrapped
function UserList({ users }) {
  return (
    <ul>
      {users.map(user => (
        <li key={user.id}>{user.name}</li>
      ))}
    </ul>
  );
}

// Enhanced component with loading functionality
const UserListWithLoading = withLoading(UserList);

// Usage
function App() {
  const [isLoading, setIsLoading] = useState(true);
  const [users, setUsers] = useState([]);
  
  useEffect(() => {
    // Fetch users
    fetch('https://api.example.com/users')
      .then(response => response.json())
      .then(data => {
        setUsers(data);
        setIsLoading(false);
      });
  }, []);
  
  return <UserListWithLoading isLoading={isLoading} users={users} />;
}
```


### Fragment Components

Fragments allow grouping multiple elements without adding extra nodes to the DOM:

```jsx
import React, { Fragment } from 'react';

function UserInfoRow({ user }) {
  return (
    <Fragment>
      <td>{user.id}</td>
      <td>{user.name}</td>
      <td>{user.email}</td>
    </Fragment>
  );
}

function UserTable({ users }) {
  return (
    <table>
      <thead>
        <tr>
          <th>ID</th>
          <th>Name</th>
          <th>Email</th>
        </tr>
      </thead>
      <tbody>
        {users.map(user => (
          <tr key={user.id}>
            <UserInfoRow user={user} />
          </tr>
        ))}
      </tbody>
    </table>
  );
}
```

The shorthand syntax `<>...</>` can also be used instead of `<Fragment>...</Fragment>`.

### Wrapper Components

Wrapper components provide common functionality or styling to their children:

```jsx
import React from 'react';

function Card({ title, children, className }) {
  return (
    <div className={`card ${className || ''}`}>
      {title && <div className="card-header">{title}</div>}
      <div className="card-body">{children}</div>
    </div>
  );
}

function Section({ title, children }) {
  return (
    <section className="section">
      {title && <h2 className="section-title">{title}</h2>}
      <div className="section-content">{children}</div>
    </section>
  );
}

// Usage
function App() {
  return (
    <Section title="User Information">
      <Card title="Profile">
        <p>Name: John Doe</p>
        <p>Email: john@example.com</p>
      </Card>
      <Card title="Settings">
        <p>Theme: Dark</p>
        <p>Notifications: On</p>
      </Card>
    </Section>
  );
}
```


# 7. Conditional and Iterative Rendering

React excels at handling dynamic content through conditional and iterative rendering techniques.

### Conditional Rendering

Conditional rendering allows components to display different content based on conditions:

```jsx
import React, { useState } from 'react';

function UserProfile({ user, isAdmin }) {
  // If/else approach
  if (!user) {
    return <div>Please log in to view your profile.</div>;
  }
  
  // Element variables
  let adminControls;
  if (isAdmin) {
    adminControls = (
      <div className="admin-panel">
        <h3>Admin Controls</h3>
        <button>Manage Users</button>
        <button>System Settings</button>
      </div>
    );
  }
  
  return (
    <div className="user-profile">
      <h2>{user.name}'s Profile</h2>
      <p>Email: {user.email}</p>
      
      {/* Ternary operator */}
      {user.bio 
        ? <p>Bio: {user.bio}</p> 
        : <p>No bio provided.</p>
      }
      
      {/* Logical && operator */}
      {user.location && <p>Location: {user.location}</p>}
      
      {/* Previously defined element variable */}
      {adminControls}
      
      {/* Inline IIFE (Immediately Invoked Function Expression) */}
      {(() => {
        if (user.status === 'active') return <span className="status-active">Active</span>;
        if (user.status === 'pending') return <span className="status-pending">Pending</span>;
        return <span className="status-inactive">Inactive</span>;
      })()}
    </div>
  );
}

// Usage
function App() {
  const [user, setUser] = useState({
    name: 'Alice Johnson',
    email: 'alice@example.com',
    bio: 'Software developer and hiking enthusiast.',
    location: 'Seattle, WA',
    status: 'active'
  });
  const [isAdmin, setIsAdmin] = useState(true);
  
  return <UserProfile user={user} isAdmin={isAdmin} />;
}
```


### Iterative Rendering

Iterative rendering allows generating multiple similar elements from arrays of data:

```jsx
import React from 'react';

function ProductList({ products, category }) {
  // Filter products by category if provided
  const displayProducts = category 
    ? products.filter(product => product.category === category)
    : products;
    
  // Basic array mapping
  return (
    <div className="product-list">
      <h2>{category ? `${category} Products` : 'All Products'}</h2>
      
      {displayProducts.length === 0 ? (
        <p>No products found.</p>
      ) : (
        <ul>
          {displayProducts.map(product => (
            <li key={product.id} className="product-item">
              <h3>{product.name}</h3>
              <p>Price: ${product.price.toFixed(2)}</p>
              <p>Rating: {product.rating}/5</p>
            </li>
          ))}
        </ul>
      )}
      
      {/* Using indices (only when appropriate) */}
      <div className="product-grid">
        {displayProducts.map((product, index) => (
          <div 
            key={product.id} 
            className={`grid-item ${index % 2 === 0 ? 'even' : 'odd'}`}
          >
            Product #{index + 1}: {product.name}
          </div>
        ))}
      </div>
      
      {/* Rendering complex nested structures */}
      <div className="categories">
        {Object.entries(
          displayProducts.reduce((acc, product) => {
            const { category } = product;
            acc[category] = acc[category] || [];
            acc[category].push(product);
            return acc;
          }, {})
        ).map(([category, categoryProducts]) => (
          <div key={category} className="category-group">
            <h3>{category}</h3>
            <ul>
              {categoryProducts.map(product => (
                <li key={product.id}>{product.name}</li>
              ))}
            </ul>
          </div>
        ))}
      </div>
    </div>
  );
}

// Usage
function App() {
  const products = [
    { id: 1, name: 'Laptop', price: 999.99, category: 'Electronics', rating: 4.5 },
    { id: 2, name: 'Headphones', price: 129.99, category: 'Electronics', rating: 4.8 },
    { id: 3, name: 'Coffee Maker', price: 79.99, category: 'Appliances', rating: 4.2 },
    { id: 4, name: 'Running Shoes', price: 89.99, category: 'Clothing', rating: 4.6 }
  ];
  
  return <ProductList products={products} />;
}
```

When rendering lists in React, each item must have a unique `key` prop to help React identify which items have changed, been added, or removed. Using array indices as keys is generally not recommended unless the list is static and will never be reordered or filtered.

# 8. React Routers

React Router is the standard library for navigation in React applications, enabling the creation of single-page applications with multiple views.

### Basic Routing Setup (React Router v6)

```jsx
import React from 'react';
import { BrowserRouter, Routes, Route, Link, Outlet, useParams, useNavigate } from 'react-router-dom';

// Page components
function Home() {
  return <h2>Home Page</h2>;
}

function About() {
  return <h2>About Us</h2>;
}

function ProductList() {
  const products = [
    { id: 1, name: 'Product 1' },
    { id: 2, name: 'Product 2' },
    { id: 3, name: 'Product 3' }
  ];
  
  return (
    <div>
      <h2>Products</h2>
      <ul>
        {products.map(product => (
          <li key={product.id}>
            <Link to={`/products/${product.id}`}>{product.name}</Link>
          </li>
        ))}
      </ul>
      <Outlet /> {/* Nested routes will render here */}
    </div>
  );
}

function ProductDetail() {
  const { productId } = useParams();
  const navigate = useNavigate();
  
  return (
    <div>
      <h3>Product {productId} Details</h3>
      <p>This is the detailed view for product {productId}.</p>
      <button onClick={() => navigate('/products')}>Back to Products</button>
    </div>
  );
}

function NotFound() {
  return <h2>404 - Page Not Found</h2>;
}

// Layout component with navigation
function Layout() {
  return (
    <div>
      <nav>
        <ul>
          <li><Link to="/">Home</Link></li>
          <li><Link to="/about">About</Link></li>
          <li><Link to="/products">Products</Link></li>
        </ul>
      </nav>
      
      <hr />
      
      {/* Outlet renders children routes */}
      <Outlet />
    </div>
  );
}

// Main App with Router configuration
function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Layout />}>
          <Route index element={<Home />} />
          <Route path="about" element={<About />} />
          <Route path="products" element={<ProductList />}>
            <Route path=":productId" element={<ProductDetail />} />
          </Route>
          <Route path="*" element={<NotFound />} />
        </Route>
      </Routes>
    </BrowserRouter>
  );
}

export default App;
```


### Protected Routes

Protected routes restrict access to certain pages based on authentication status:

```jsx
import React, { useState } from 'react';
import { BrowserRouter, Routes, Route, Navigate, useLocation } from 'react-router-dom';

// Authentication context (simplified)
const AuthContext = React.createContext(null);

function AuthProvider({ children }) {
  const [user, setUser] = useState(null);
  
  const login = (username) => {
    setUser({ username });
  };
  
  const logout = () => {
    setUser(null);
  };
  
  return (
    <AuthContext.Provider value={{ user, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
}

// Protected route component
function RequireAuth({ children }) {
  const { user } = React.useContext(AuthContext);
  const location = useLocation();
  
  if (!user) {
    // Redirect to login page but save the current location
    return <Navigate to="/login" state={{ from: location }} replace />;
  }
  
  return children;
}

// Login page
function Login() {
  const { login } = React.useContext(AuthContext);
  const location = useLocation();
  const navigate = useNavigate();
  const [username, setUsername] = useState('');
  
  // Get the previous location or default to home
  const from = location.state?.from?.pathname || '/';
  
  const handleSubmit = (e) => {
    e.preventDefault();
    login(username);
    navigate(from, { replace: true });
  };
  
  return (
    <div>
      <h2>Login</h2>
      <p>You must log in to view the page at {from}</p>
      <form onSubmit={handleSubmit}>
        <input
          type="text"
          value={username}
          onChange={(e) => setUsername(e.target.value)}
          placeholder="Username"
        />
        <button type="submit">Login</button>
      </form>
    </div>
  );
}

// Dashboard (protected page)
function Dashboard() {
  const { user, logout } = React.useContext(AuthContext);
  
  return (
    <div>
      <h2>Dashboard</h2>
      <p>Welcome, {user.username}!</p>
      <button onClick={logout}>Logout</button>
    </div>
  );
}

// Routes setup
function App() {
  return (
    <AuthProvider>
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/login" element={<Login />} />
          <Route 
            path="/dashboard" 
            element={
              <RequireAuth>
                <Dashboard />
              </RequireAuth>
            } 
          />
        </Routes>
      </BrowserRouter>
    </AuthProvider>
  );
}
```


# 9. Counter Component

A simple counter component with increment, decrement, and reset functionality:

```jsx
import React, { useState } from 'react';

function Counter() {
  const [count, setCount] = useState(0);
  
  const increment = () => {
    setCount(prevCount => prevCount + 1);
  };
  
  const decrement = () => {
    setCount(prevCount => prevCount - 1);
  };
  
  const reset = () => {
    setCount(0);
  };
  
  return (
    <div className="counter">
      <h2>Counter</h2>
      <p className="count">Current Count: {count}</p>
      <div className="button-group">
        <button onClick={increment}>Increment</button>
        <button onClick={decrement}>Decrement</button>
        <button onClick={reset}>Reset</button>
      </div>
    </div>
  );
}

export default Counter;
```

This counter example demonstrates the use of the useState hook for managing the count state. The component renders the current count and three buttons to modify it. Using the functional form of setState (`prevCount => prevCount + 1`) ensures that updates are based on the latest state, preventing race conditions when state updates happen in quick succession.

# 10. User Login Form

A login form with validation and controlled inputs:

```jsx
import React, { useState } from 'react';

function LoginForm() {
  const [formData, setFormData] = useState({
    username: '',
    password: ''
  });
  const [errors, setErrors] = useState({});
  const [isSubmitting, setIsSubmitting] = useState(false);
  
  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prevData => ({
      ...prevData,
      [name]: value
    }));
    
    // Clear error when user starts typing
    if (errors[name]) {
      setErrors(prevErrors => ({
        ...prevErrors,
        [name]: null
      }));
    }
  };
  
  const validateForm = () => {
    const newErrors = {};
    
    if (!formData.username.trim()) {
      newErrors.username = 'Username is required';
    }
    
    if (!formData.password) {
      newErrors.password = 'Password is required';
    } else if (formData.password.length < 6) {
      newErrors.password = 'Password must be at least 6 characters';
    }
    
    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };
  
  const handleSubmit = (e) => {
    e.preventDefault();
    setIsSubmitting(true);
    
    if (validateForm()) {
      // Simulate API call
      console.log('Form submitted successfully:', formData);
      setTimeout(() => {
        alert('Login successful!');
        setIsSubmitting(false);
        // Reset form after successful submission
        setFormData({ username: '', password: '' });
      }, 1000);
    } else {
      setIsSubmitting(false);
    }
  };
  
  return (
    <div className="login-form-container">
      <h2>Login</h2>
      <form onSubmit={handleSubmit}>
        <div className="form-group">
          <label htmlFor="username">Username</label>
          <input
            type="text"
            id="username"
            name="username"
            value={formData.username}
            onChange={handleChange}
            className={errors.username ? 'error' : ''}
          />
          {errors.username && <span className="error-message">{errors.username}</span>}
        </div>
        
        <div className="form-group">
          <label htmlFor="password">Password</label>
          <input
            type="password"
            id="password"
            name="password"
            value={formData.password}
            onChange={handleChange}
            className={errors.password ? 'error' : ''}
          />
          {errors.password && <span className="error-message">{errors.password}</span>}
        </div>
        
        <button 
          type="submit" 
          disabled={isSubmitting}
        >
          {isSubmitting ? 'Logging in...' : 'Login'}
        </button>
      </form>
    </div>
  );
}

export default LoginForm;
```

This login form demonstrates controlled inputs, form validation, and error handling. The component uses the useState hook to manage both the form data and validation errors. When the form is submitted, it validates the inputs and displays appropriate error messages if validation fails. If validation passes, it simulates an API call and shows a success message.

# 11. To-Do List Application

A complete to-do list application with add, complete, and delete functionality:

```jsx
import React, { useState, useEffect } from 'react';

function TodoList() {
  const [todos, setTodos] = useState(() => {
    // Initialize from localStorage if available
    const savedTodos = localStorage.getItem('todos');
    return savedTodos ? JSON.parse(savedTodos) : [];
  });
  const [newTodo, setNewTodo] = useState('');
  
  // Save todos to localStorage whenever they change
  useEffect(() => {
    localStorage.setItem('todos', JSON.stringify(todos));
  }, [todos]);
  
  const handleNewTodoChange = (e) => {
    setNewTodo(e.target.value);
  };
  
  const handleAddTodo = (e) => {
    e.preventDefault();
    
    if (!newTodo.trim()) return;
    
    const todo = {
      id: Date.now(),
      text: newTodo.trim(),
      completed: false,
      createdAt: new Date().toISOString()
    };
    
    setTodos([...todos, todo]);
    setNewTodo('');
  };
  
  const toggleTodoCompletion = (id) => {
    setTodos(todos.map(todo => 
      todo.id === id ? { ...todo, completed: !todo.completed } : todo
    ));
  };
  
  const deleteTodo = (id) => {
    setTodos(todos.filter(todo => todo.id !== id));
  };
  
  const clearCompletedTodos = () => {
    setTodos(todos.filter(todo => !todo.completed));
  };
  
  // Count remaining active todos
  const activeTodoCount = todos.filter(todo => !todo.completed).length;
  
  return (
    <div className="todo-list">
      <h2>Todo List</h2>
      
      <form onSubmit={handleAddTodo}>
        <input
          type="text"
          value={newTodo}
          onChange={handleNewTodoChange}
          placeholder="What needs to be done?"
        />
        <button type="submit">Add Todo</button>
      </form>
      
      <div className="todo-stats">
        <span>{activeTodoCount} items left</span>
        {todos.length > activeTodoCount && (
          <button onClick={clearCompletedTodos}>Clear Completed</button>
        )}
      </div>
      
      {todos.length > 0 ? (
        <ul>
          {todos.map(todo => (
            <li key={todo.id} className={todo.completed ? 'completed' : ''}>
              <input
                type="checkbox"
                checked={todo.completed}
                onChange={() => toggleTodoCompletion(todo.id)}
              />
              <span className="todo-text">{todo.text}</span>
              <button onClick={() => deleteTodo(todo.id)}>Delete</button>
            </li>
          ))}
        </ul>
      ) : (
        <p className="empty-state">No todos yet. Add a todo to get started!</p>
      )}
    </div>
  );
}

export default TodoList;
```

This to-do list application demonstrates several React concepts including state management, form handling, conditional rendering, and persisting data with localStorage. The component uses the useState hook to manage the list of todos and the new todo input field. It also uses the useEffect hook to save the todos to localStorage whenever they change, ensuring that the todos persist between page refreshes.

# 12. Theme Switcher

A component that toggles between light and dark themes:

```jsx
import React, { useState, useEffect } from 'react';

function ThemeSwitcher() {
  const [theme, setTheme] = useState(() => {
    // Initialize from localStorage or default to 'light'
    return localStorage.getItem('theme') || 'light';
  });
  
  // Apply the theme to the document
  useEffect(() => {
    document.body.className = `theme-${theme}`;
    localStorage.setItem('theme', theme);
  }, [theme]);
  
  const toggleTheme = () => {
    setTheme(prevTheme => prevTheme === 'light' ? 'dark' : 'light');
  };
  
  return (
    <div className="theme-switcher">
      <h2>Theme Switcher</h2>
      <div className="theme-demo" style={{
        backgroundColor: theme === 'light' ? '#fff' : '#333',
        color: theme === 'light' ? '#333' : '#fff',
        padding: '20px',
        borderRadius: '8px',
        transition: 'all 0.3s ease'
      }}>
        <h3>Current Theme: {theme.charAt(0).toUpperCase() + theme.slice(1)}</h3>
        <p>
          This is how the content looks in the {theme} theme. The background and text
          colors adjust automatically based on the selected theme.
        </p>
      </div>
      <button onClick={toggleTheme}>
        Switch to {theme === 'light' ? 'Dark' : 'Light'} Mode
      </button>
    </div>
  );
}

export default ThemeSwitcher;
```

This theme switcher demonstrates using the useState and useEffect hooks to manage and apply themes in a React application. The component initializes the theme from localStorage (if available) or defaults to 'light'. When the theme changes, the useEffect hook updates the document's body class and saves the theme preference to localStorage. The component renders a demo area that displays how content looks in the current theme, along with a button to toggle between light and dark modes.

# 13. Parent-Child Communication

Components demonstrating data flow between parent and child components:

```jsx
import React, { useState } from 'react';

// Child component receiving props from parent
function ChildComponent({ message, onReply }) {
  const [reply, setReply] = useState('');
  
  const handleSendReply = () => {
    if (reply.trim()) {
      onReply(reply);
      setReply('');
    }
  };
  
  return (
    <div className="child-component">
      <h3>Child Component</h3>
      <p>Message from parent: <strong>{message}</strong></p>
      <div>
        <input
          type="text"
          value={reply}
          onChange={(e) => setReply(e.target.value)}
          placeholder="Type a reply..."
        />
        <button onClick={handleSendReply}>Send Reply</button>
      </div>
    </div>
  );
}

// Parent component that passes data to child and receives data back
function ParentComponent() {
  const [messages, setMessages] = useState([
    { id: 1, text: 'Hello from parent!', fromParent: true }
  ]);
  const [newMessage, setNewMessage] = useState('');
  
  const sendMessageToChild = () => {
    if (newMessage.trim()) {
      setMessages([...messages, {
        id: Date.now(),
        text: newMessage,
        fromParent: true
      }]);
      setNewMessage('');
    }
  };
  
  const handleReplyFromChild = (reply) => {
    setMessages([...messages, {
      id: Date.now(),
      text: reply,
      fromParent: false
    }]);
  };
  
  // Get the latest message from parent to pass to child
  const latestParentMessage = [...messages]
    .filter(m => m.fromParent)
    .pop()?.text || 'No message yet';
  
  return (
    <div className="parent-component">
      <h2>Parent Component</h2>
      <div className="message-input">
        <input
          type="text"
          value={newMessage}
          onChange={(e) => setNewMessage(e.target.value)}
          placeholder="Send a message to child..."
        />
        <button onClick={sendMessageToChild}>Send Message</button>
      </div>
      
      <div className="message-history">
        <h3>Message History:</h3>
        <ul>
          {messages.map(msg => (
            <li key={msg.id} className={msg.fromParent ? 'from-parent' : 'from-child'}>
              <strong>{msg.fromParent ? 'Parent' : 'Child'}:</strong> {msg.text}
            </li>
          ))}
        </ul>
      </div>
      
      <div className="child-container">
        <ChildComponent 
          message={latestParentMessage}
          onReply={handleReplyFromChild}
        />
      </div>
    </div>
  );
}

export default ParentComponent;
```

This example demonstrates communication between parent and child components using props and callback functions. The ParentComponent passes data to the ChildComponent through the `message` prop and receives data back through the `onReply` callback function. The ParentComponent maintains a list of messages exchanged between the components and displays them in a message history. This pattern illustrates the unidirectional data flow that is fundamental to React's component model.

# 14. Conditional Authentication UI

A component that shows different UI elements based on authentication state:

```jsx
import React, { useState } from 'react';

function AuthenticationUI() {
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const [userData, setUserData] = useState(null);
  
  const handleLogin = (e) => {
    e.preventDefault();
    setError('');
    
    // Simple validation
    if (!username.trim() || !password) {
      setError('Please enter both username and password');
      return;
    }
    
    // Simulate authentication
    if (username === 'demo' && password === 'password') {
      setIsLoggedIn(true);
      setUserData({
        username: 'demo',
        name: 'Demo User',
        email: 'demo@example.com',
        role: 'User'
      });
    } else {
      setError('Invalid username or password');
    }
  };
  
  const handleLogout = () => {
    setIsLoggedIn(false);
    setUsername('');
    setPassword('');
    setUserData(null);
  };
  
  return (
    <div className="auth-container">
      <h2>Authentication Demo</h2>
      
      {isLoggedIn ? (
        <div className="user-profile">
          <h3>Welcome, {userData.name}!</h3>
          <div className="user-info">
            <p><strong>Username:</strong> {userData.username}</p>
            <p><strong>Email:</strong> {userData.email}</p>
            <p><strong>Role:</strong> {userData.role}</p>
          </div>
          <button onClick={handleLogout}>Logout</button>
        </div>
      ) : (
        <div className="login-form">
          <h3>Please Log In</h3>
          {error && <div className="error-message">{error}</div>}
          <form onSubmit={handleLogin}>
            <div className="form-group">
              <label htmlFor="username">Username</label>
              <input
                type="text"
                id="username"
                value={username}
                onChange={(e) => setUsername(e.target.value)}
              />
            </div>
            <div className="form-group">
              <label htmlFor="password">Password</label>
              <input
                type="password"
                id="password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
              />
            </div>
            <button type="submit">Login</button>
            <p className="hint">Try username: "demo" and password: "password"</p>
          </form>
        </div>
      )}
    </div>
  );
}

export default AuthenticationUI;
```

This example demonstrates conditional rendering based on authentication state. The component uses the useState hook to track whether the user is logged in and displays either a login form or user profile accordingly. When the user submits the login form, the component validates the credentials and updates the login state. If authentication succeeds, it displays the user's profile information. If authentication fails, it displays an error message. The component also provides a logout button that resets the state.

# 15. Timer Component with useEffect

A timer component that demonstrates the useEffect hook for side effects:

```jsx
import React, { useState, useEffect, useRef } from 'react';

function Timer() {
  const [seconds, setSeconds] = useState(0);
  const [isActive, setIsActive] = useState(false);
  const intervalRef = useRef(null);
  
  // Set up or clear the timer based on isActive state
  useEffect(() => {
    if (isActive) {
      intervalRef.current = setInterval(() => {
        setSeconds(prevSeconds => prevSeconds + 1);
      }, 1000);
    } else if (intervalRef.current) {
      clearInterval(intervalRef.current);
    }
    
    // Cleanup function that runs when component unmounts or effect re-runs
    return () => {
      if (intervalRef.current) {
        clearInterval(intervalRef.current);
      }
    };
  }, [isActive]);
  
  const toggleTimer = () => {
    setIsActive(!isActive);
  };
  
  const resetTimer = () => {
    setIsActive(false);
    setSeconds(0);
  };
  
  // Format seconds as mm:ss
  const formatTime = (totalSeconds) => {
    const minutes = Math.floor(totalSeconds / 60);
    const seconds = totalSeconds % 60;
    return `${minutes.toString().padStart(2, '0')}:${seconds.toString().padStart(2, '0')}`;
  };
  
  return (
    <div className="timer">
      <h2>Timer</h2>
      <div className="timer-display">
        {formatTime(seconds)}
      </div>
      <div className="timer-controls">
        <button onClick={toggleTimer}>
          {isActive ? 'Pause' : 'Start'}
        </button>
        <button onClick={resetTimer}>Reset</button>
      </div>
    </div>
  );
}

export default Timer;
```

This timer component demonstrates the use of the useEffect hook to manage side effects in a functional component. The component uses the useState hook to track the timer value and whether the timer is active. It uses the useRef hook to store a reference to the interval, allowing it to be cleared in the cleanup function. The useEffect hook sets up an interval when the timer is active and clears it when the timer is paused or the component unmounts. This pattern ensures that the interval is properly cleaned up, preventing memory leaks.

# 16. Searchable List Component

A component that filters a list based on a search term:

```jsx
import React, { useState, useMemo } from 'react';

function SearchableList() {
  const [searchTerm, setSearchTerm] = useState('');
  
  // Sample data - in a real app, this might come from props or an API
  const users = [
    { id: 1, name: 'John Doe', email: 'john@example.com', role: 'Admin' },
    { id: 2, name: 'Jane Smith', email: 'jane@example.com', role: 'User' },
    { id: 3, name: 'Bob Johnson', email: 'bob@example.com', role: 'Editor' },
    { id: 4, name: 'Alice Williams', email: 'alice@example.com', role: 'User' },
    { id: 5, name: 'Charlie Brown', email: 'charlie@example.com', role: 'Viewer' },
    { id: 6, name: 'Diana Prince', email: 'diana@example.com', role: 'Admin' },
    { id: 7, name: 'Edward Norton', email: 'edward@example.com', role: 'Editor' },
    { id: 8, name: 'Fiona Apple', email: 'fiona@example.com', role: 'User' }
  ];
  
  // Filter users based on search term
  const filteredUsers = useMemo(() => {
    const term = searchTerm.toLowerCase().trim();
    
    if (!term) return users;
    
    return users.filter(user => 
      user.name.toLowerCase().includes(term) ||
      user.email.toLowerCase().includes(term) ||
      user.role.toLowerCase().includes(term)
    );
  }, [searchTerm, users]);
  
  const handleSearchChange = (e) => {
    setSearchTerm(e.target.value);
  };
  
  return (
    <div className="searchable-list">
      <h2>User Directory</h2>
      
      <div className="search-bar">
        <input
          type="text"
          value={searchTerm}
          onChange={handleSearchChange}
          placeholder="Search by name, email, or role..."
        />
        {searchTerm && (
          <button onClick={() => setSearchTerm('')}>Clear</button>
        )}
      </div>
      
      <div className="results-info">
        {filteredUsers.length === users.length ? (
          <p>Showing all {users.length} users</p>
        ) : (
          <p>Showing {filteredUsers.length} of {users.length} users</p>
        )}
      </div>
      
      {filteredUsers.length > 0 ? (
        <table className="users-table">
          <thead>
            <tr>
              <th>Name</th>
              <th>Email</th>
              <th>Role</th>
            </tr>
          </thead>
          <tbody>
            {filteredUsers.map(user => (
              <tr key={user.id}>
                <td>{user.name}</td>
                <td>{user.email}</td>
                <td>{user.role}</td>
              </tr>
            ))}
          </tbody>
        </table>
      ) : (
        <p className="no-results">No users match your search criteria.</p>
      )}
    </div>
  );
}

export default SearchableList;
```

This searchable list component demonstrates filtering data based on user input. The component uses the useState hook to track the search term and the useMemo hook to efficiently filter the list only when the search term or source data changes. The component displays a list of users in a table format and allows the user to filter the list by typing in a search box. The search functionality matches against multiple fields (name, email, and role), providing a comprehensive search experience.

# 17.  Event Bubbling in React

Event bubbling is a key aspect of how events propagate in the DOM, with React's synthetic event system handling this behavior:

```jsx
import React, { useState } from 'react';

function EventBubblingDemo() {
  const [logs, setLogs] = useState([]);
  
  const addLog = (message) => {
    setLogs(prevLogs => [...prevLogs, {
      id: Date.now(),
      message
    }]);
  };
  
  const handleOuterClick = (e) => {
    addLog('Outer div clicked');
  };
  
  const handleMiddleClick = (e) => {
    addLog('Middle div clicked');
    // e.stopPropagation(); // Uncomment to stop bubbling
  };
  
  const handleInnerClick = (e) => {
    addLog('Inner button clicked');
    // e.stopPropagation(); // Uncomment to stop bubbling
  };
  
  const clearLogs = () => {
    setLogs([]);
  };
  
  return (
    <div className="event-bubbling-demo">
      <h2>Event Bubbling Demo</h2>
      <p>
        Click on the nested elements to see how events bubble up from the innermost element
        to its parents. Events in React bubble just as they do in standard DOM.
      </p>
      
      <div 
        className="outer-element"
        onClick={handleOuterClick}
        style={{ padding: '20px', backgroundColor: '#f0f0f0', border: '1px solid #ccc' }}
      >
        Outer Element
        
        <div 
          className="middle-element"
          onClick={handleMiddleClick}
          style={{ padding: '20px', margin: '20px', backgroundColor: '#e0e0e0', border: '1px solid #bbb' }}
        >
          Middle Element
          
          <button 
            onClick={handleInnerClick}
            style={{ margin: '20px' }}
          >
            Inner Button
          </button>
        </div>
      </div>
      
      <div className="event-log">
        <div className="log-header">
          <h3>Event Log</h3>
          <button onClick={clearLogs}>Clear Log</button>
        </div>
        
        {logs.length > 0 ? (
          <ul>
            {logs.map(log => (
              <li key={log.id}>{log.message}</li>
            ))}
          </ul>
        ) : (
          <p>No events logged yet. Try clicking on the elements above.</p>
        )}
      </div>
    </div>
  );
}

export default EventBubblingDemo;
```

This example demonstrates event bubbling in React, where events triggered on a child element will bubble up to its parent elements. When the inner button is clicked, all three event handlers are triggered in order from innermost to outermost: first the button's click handler, then the middle div's, and finally the outer div's. The component also shows how to stop event propagation using `e.stopPropagation()`, which prevents the event from bubbling up further.

# 18. CSS Module Integration

CSS Modules allow for component-scoped styling, preventing style conflicts:

```jsx
// Button.module.css
.button {
  padding: 10px 15px;
  border-radius: 4px;
  font-weight: bold;
  cursor: pointer;
  transition: all 0.3s ease;
}

.primary {
  background-color: #0070f3;
  color: white;
  border: 1px solid #0070f3;
}

.secondary {
  background-color: #f5f5f5;
  color: #333;
  border: 1px solid #ddd;
}

.large {
  font-size: 18px;
  padding: 12px 20px;
}

.small {
  font-size: 14px;
  padding: 6px 12px;
}
```

```jsx
// Button.js
import React from 'react';
import styles from './Button.module.css';

function Button({ children, variant = 'primary', size = 'medium', onClick, className, ...props }) {
  const buttonClass = [
    styles.button,
    styles[variant],
    size === 'large' ? styles.large : size === 'small' ? styles.small : '',
    className // Allow additional class names to be passed
  ].filter(Boolean).join(' ');
  
  return (
    <button
      className={buttonClass}
      onClick={onClick}
      {...props}
    >
      {children}
    </button>
  );
}

export default Button;
```

```jsx
// App.js using the Button component
import React from 'react';
import Button from './Button';

function App() {
  return (
    <div>
      <h2>CSS Modules Example</h2>
      <div className="button-showcase">
        <Button variant="primary" onClick={() => alert('Primary clicked')}>
          Primary Button
        </Button>
        
        <Button variant="secondary" onClick={() => alert('Secondary clicked')}>
          Secondary Button
        </Button>
        
        <Button variant="primary" size="large">
          Large Button
        </Button>
        
        <Button variant="secondary" size="small">
          Small Button
        </Button>
      </div>
    </div>
  );
}

export default App;
```

CSS Modules work by automatically generating unique class names during the build process, ensuring that styles remain scoped to their respective components. This approach prevents global style conflicts that can occur with traditional CSS. Each component imports its own CSS module and uses the generated class names to style its elements. The result is modular, maintainable styling that doesn't interfere with other components.

# 19. Data Binding in React

Data binding in React is primarily one-way, from state to UI, with explicit handlers for UI-to-state updates:

```jsx
import React, { useState } from 'react';

function DataBindingDemo() {
  const [formData, setFormData] = useState({
    firstName: '',
    lastName: '',
    email: '',
    age: ''
  });
  
  const [submitted, setSubmitted] = useState(false);
  
  // Handle input changes - updates state when input changes
  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prevData => ({
      ...prevData,
      [name]: value
    }));
  };
  
  // Handle form submission
  const handleSubmit = (e) => {
    e.preventDefault();
    setSubmitted(true);
    console.log('Form submitted with data:', formData);
  };
  
  return (
    <div className="data-binding-demo">
      <h2>Data Binding in React</h2>
      
      <form onSubmit={handleSubmit}>
        <div className="form-grid">
          <div className="form-group">
            <label htmlFor="firstName">First Name</label>
            <input
              type="text"
              id="firstName"
              name="firstName"
              value={formData.firstName}
              onChange={handleChange}
            />
          </div>
          
          <div className="form-group">
            <label htmlFor="lastName">Last Name</label>
            <input
              type="text"
              id="lastName"
              name="lastName"
              value={formData.lastName}
              onChange={handleChange}
            />
          </div>
          
          <div className="form-group">
            <label htmlFor="email">Email</label>
            <input
              type="email"
              id="email"
              name="email"
              value={formData.email}
              onChange={handleChange}
            />
          </div>
          
          <div className="form-group">
            <label htmlFor="age">Age</label>
            <input
              type="number"
              id="age"
              name="age"
              value={formData.age}
              onChange={handleChange}
            />
          </div>
        </div>
        
        <button type="submit">Submit</button>
      </form>
      
      <div className="preview-section">
        <h3>Data Preview (One-way Binding)</h3>
        <p>As you type in the form, this preview updates automatically:</p>
        <div className="preview-data">
          <p>First Name: <strong>{formData.firstName}</strong></p>
          <p>Last Name: <strong>{formData.lastName}</strong></p>
          <p>Email: <strong>{formData.email}</strong></p>
          <p>Age: <strong>{formData.age}</strong></p>
        </div>
      </div>
      
      {submitted && (
        <div className="submission-result">
          <h3>Form Submitted!</h3>
          <p>The following data was submitted:</p>
          <pre>{JSON.stringify(formData, null, 2)}</pre>
        </div>
      )}
    </div>
  );
}

export default DataBindingDemo;
```

This example demonstrates React's one-way data binding approach. The component maintains form data in state using the useState hook. Each input's value is bound to the corresponding state value, creating one-way binding from state to UI. When users type in the inputs, the `handleChange` function updates the state, which then updates the UI. The preview section demonstrates how changes to the form data are immediately reflected in the UI. This one-way data flow makes applications more predictable and easier to debug compared to two-way binding systems.

# 20. Event Handling in React

React provides a consistent system for handling various types of events:

```jsx
import React, { useState } from 'react';

function EventHandlingDemo() {
  const [position, setPosition] = useState({ x: 0, y: 0 });
  const [keyPressed, setKeyPressed] = useState('None');
  const [inputText, setInputText] = useState('');
  const [formData, setFormData] = useState({ name: '', email: '' });
  const [isHovering, setIsHovering] = useState(false);
  
  // Mouse events
  const handleMouseMove = (e) => {
    setPosition({
      x: e.clientX,
      y: e.clientY
    });
  };
  
  // Keyboard events
  const handleKeyDown = (e) => {
    setKeyPressed(e.key);
  };
  
  // Form events
  const handleInputChange = (e) => {
    setInputText(e.target.value);
  };
  
  const handleFormChange = (e) => {
    const { name, value } = e.target;
    setFormData(prevData => ({
      ...prevData,
      [name]: value
    }));
  };
  
  const handleSubmit = (e) => {
    e.preventDefault();
    alert(`Form submitted with name: ${formData.name} and email: ${formData.email}`);
  };
  
  // Focus events
  const handleFocus = () => {
    console.log('Input focused');
  };
  
  const handleBlur = () => {
    console.log('Input blurred (lost focus)');
  };
  
  // Mouse enter/leave events
  const handleMouseEnter = () => {
    setIsHovering(true);
  };
  
  const handleMouseLeave = () => {
    setIsHovering(false);
  };
  
  return (
    <div className="event-handling-demo">
      <h2>Event Handling in React</h2>
      
      <section>
        <h3>Mouse Events</h3>
        <div 
          className="mouse-area"
          onMouseMove={handleMouseMove}
          style={{
            height: '100px',
            backgroundColor: '#f0f0f0',
            border: '1px solid #ccc',
            display: 'flex',
            justifyContent: 'center',
            alignItems: 'center'
          }}
        >
          Move mouse here: Position: X={position.x}, Y={position.y}
        </div>
      </section>
      
      <section>
        <h3>Keyboard Events</h3>
        <div className="keyboard-area">
          <p>Press any key (click here first)</p>
          <input
            type="text"
            onKeyDown={handleKeyDown}
            placeholder="Press any key"
          />
          <p>Last key pressed: <strong>{keyPressed}</strong></p>
        </div>
      </section>
      
      <section>
        <h3>Form Events</h3>
        <div className="form-events">
          <div>
            <label>Simple Input (onChange):</label>
            <input
              type="text"
              value={inputText}
              onChange={handleInputChange}
              onFocus={handleFocus}
              onBlur={handleBlur}
              placeholder="Type something..."
            />
            <p>You typed: {inputText}</p>
          </div>
          
          <form onSubmit={handleSubmit}>
            <div>
              <label>Name:</label>
              <input
                type="text"
                name="name"
                value={formData.name}
                onChange={handleFormChange}
                placeholder="Your name"
              />
            </div>
            <div>
              <label>Email:</label>
              <input
                type="email"
                name="email"
                value={formData.email}
                onChange={handleFormChange}
                placeholder="Your email"
              />
            </div>
            <button type="submit">Submit Form</button>
          </form>
        </div>
      </section>
      
      <section>
        <h3>Mouse Enter/Leave Events</h3>
        <div
          className="hover-area"
          onMouseEnter={handleMouseEnter}
          onMouseLeave={handleMouseLeave}
          style={{
            backgroundColor: isHovering ? '#c8e6c9' : '#f0f0f0',
            padding: '20px',
            border: '1px solid #ccc',
            transition: 'background-color 0.3s ease'
          }}
        >
          {isHovering 
            ? 'Mouse is over this element!' 
            : 'Hover over this element...'}
        </div>
      </section>
    </div>
  );
}

export default EventHandlingDemo;
```

This example demonstrates handling various types of events in React including mouse events, keyboard events, form events, and focus events. React uses a synthetic event system that normalizes browser differences, providing a consistent API across different browsers. Event handlers in React are camelCased (e.g., `onClick` instead of `onclick`) and receive a synthetic event object as their argument. The event object has methods like `preventDefault()` and `stopPropagation()` that work the same way across browsers.

## Conclusion

This comprehensive guide has covered the core concepts of React development, from component fundamentals to advanced patterns and techniques. We've explored class and functional components, state and lifecycle management, props communication, rendering patterns, routing, and practical implementations of common UI patterns.

The evolution of React has brought significant improvements to the developer experience, with hooks simplifying state management and side effects in functional components. Modern React development favors functional components with hooks over class components, though understanding both approaches remains valuable, especially when working with legacy codebases.

As React continues to evolve, staying updated with best practices is crucial. The concepts covered in this guide form a solid foundation for building high-quality, maintainable React applications. By mastering these fundamentals and applying them thoughtfully, developers can create robust user interfaces that provide excellent user experiences while remaining maintainable and extensible for future requirements.


## Stay Connected !  

If you found these notes helpful, consider **starring ⭐ this repository** and sharing it with others! Your feedback and contributions are always welcome. 😊  

### 📢 Connect with Me  

🔗 **Email:** [vigneshpraveenofficial](mailto:vigneshpraveenofficial@gmail.com)  
  
**GitHub:** [vigneshpraveen-official](https://github.com/vigneshpraveen-official)  
**LinkedIn:** [Vigneshpraveen A](https://linkedin.com/in/vigneshpraveen)  
**Instagram:** [@vignesh_praveen_official_](https://www.instagram.com/vignesh_praveen_official_)   
**LeetCode:** [vigneshpraveen-official](https://leetcode.com/u/vigneshpraveen-official/)  
**HackerRank:** [vigneshpraveeno1](https://www.hackerrank.com/profile/vigneshpraveeno1)  
**CodeChef:** [vigneshpraveen](https://www.codechef.com/users/vigneshpraveen)  
**HackerEarth:** [Vigneshpraveen](https://www.hackerearth.com/@vigneshpraveenofficial)  
**SkillRack:** [Profile](https://www.skillrack.com/faces/resume.xhtml?id=438850&key=13e533ac132be50994c9366b1a07c36a784f6db6)  

---

⭐ **Feel free to contribute, suggest improvements, and let's grow together!** 🚀  
